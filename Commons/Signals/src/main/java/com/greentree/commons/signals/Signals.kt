package com.greentree.commons.signals

import com.greentree.commons.util.EmptyAutoCloseable
import kotlin.reflect.KProperty

// An named symbol/brand for detecting Signal instances even when they weren't
// created using the same signals library version.
private val BRAND_SYMBOL = "preact-signals"

// Flags for Computed and Effect.
private const val RUNNING = 1 shl 0
private const val NOTIFIED = 1 shl 1
private const val OUTDATED = 1 shl 2
private const val DISPOSED = 1 shl 3
private const val HAS_ERROR = 1 shl 4
private const val TRACKING = 1 shl 5

// A linked list node used to track dependencies (sources) and dependents (targets).
// Also used to remember the source's last version number that the target saw.
internal class Node {

	// A source whose value the target depends on.
	var source: Signal<*>? = null
	var prevSource: Node? = null
	var nextSource: Node? = null

	// A target that depends on the source and should be notified when the source changes.
	var target: Any? = null // Computed or Effect
	var prevTarget: Node? = null
	var nextTarget: Node? = null

	// The version number of the source that target has last seen. We use version numbers
	// instead of storing the source value, because source values can take arbitrary amount
	// of memory, and computeds could hang on to them forever because they're lazily evaluated.
	// Use the special value -1 to mark potentially unused but recyclable nodes.
	var version: Int = 0

	// Used to remember & roll back the source's previous `._node` value when entering &
	// exiting a new evaluation context.
	var rollbackNode: Node? = null
}

private fun startBatch() {
	batchDepth++
}

private fun endBatch() {
	if(batchDepth > 1) {
		batchDepth--
		return
	}
	var error: Throwable? = null
	var hasError = false

	while(batchedEffect != null) {
		var effect: Effect? = batchedEffect
		batchedEffect = null

		batchIteration++

		while(effect != null) {
			val next: Effect? = effect.nextBatchedEffect
			effect.nextBatchedEffect = null
			effect.flags = effect.flags and NOTIFIED.inv()

			if((effect.flags and DISPOSED) == 0 && needsToRecompute(effect)) {
				try {
					effect.callback()
				} catch(err: Throwable) {
					if(!hasError) {
						error = err
						hasError = true
					}
				}
			}
			effect = next
		}
	}
	batchIteration = 0
	batchDepth--

	if(hasError) {
		throw error!!
	}
}

/**
 * Combine multiple value updates into one "commit" at the end of the provided callback.
 *
 * Batches can be nested and changes are only flushed once the outermost batch callback
 * completes.
 *
 * Accessing a signal that has been modified within a batch will reflect its updated
 * value.
 *
 * @param fn The callback function.
 * @returns The value returned by the callback.
 */
fun <T> batch(fn: () -> T): T {
	if(batchDepth > 0) {
		return fn()
	}
	startBatch()
	try {
		return fn()
	} finally {
		endBatch()
	}
}

// Currently evaluated computed or effect.
private var evalContext: Any? = null // Computed or Effect

/**
 * Run a callback function that can access signal values without
 * subscribing to the signal updates.
 *
 * @param fn The callback function.
 * @returns The value returned by the callback.
 */
fun <T> untracked(fn: () -> T): T {
	val prevContext = evalContext
	evalContext = null
	try {
		return fn()
	} finally {
		evalContext = prevContext
	}
}

// Effects collected into a batch.
private var batchedEffect: Effect? = null
private var batchDepth = 0
private var batchIteration = 0

// A global version number for signals, used for fast-pathing repeated
// computed.peek()/computed.value calls when nothing has changed globally.
private var globalVersion = 0

private fun addDependency(signal: Signal<*>): Node? {
	if(evalContext == null) {
		return null
	}
	val context = evalContext!! // Computed or Effect
	var node = signal.node
	if(node == null || node.target !== context) {
		/**
		 * `signal` is a new dependency. Create a new dependency node, and set it
		 * as the tail of the current context's dependency list. e.g:
		 *
		 * { A <-> B       }
		 *         ↑     ↑
		 *        tail  node (new)
		 *               ↓
		 * { A <-> B <-> C }
		 *               ↑
		 *              tail (evalContext._sources)
		 */
		node = Node().apply {
			version = 0
			source = signal
			prevSource = when(context) {
				is Computed<*> -> context.sources
				is Effect -> context.sources
				else -> null
			}
			nextSource = null
			target = context
			prevTarget = null
			nextTarget = null
			rollbackNode = signal.node
		}

		when(context) {
			is Computed<*> -> {
				if(context.sources != null) {
					context.sources!!.nextSource = node
				}
				context.sources = node
			}

			is Effect -> {
				if(context.sources != null) {
					context.sources!!.nextSource = node
				}
				context.sources = node
			}
		}
		signal.node = node
		// Subscribe to change notifications from this dependency if we're in an effect
		// OR evaluating a computed signal that in turn has subscribers.
		val flags = when(context) {
			is Computed<*> -> context.flags
			is Effect -> context.flags
			else -> 0
		}
		if((flags and TRACKING) != 0) {
			signal.subscribe(node)
		}
		return node
	} else if(node.version == -1) {
		// `signal` is an existing dependency from a previous evaluation. Reuse it.
		node.version = 0
		/**
		 * If `node` is not already the current tail of the dependency list (i.e.
		 * there is a next node in the list), then make the `node` the new tail. e.g:
		 *
		 * { A <-> B <-> C <-> D }
		 *         ↑           ↑
		 *        node   ┌─── tail (evalContext._sources)
		 *         └─────│─────┐
		 *               ↓     ↓
		 * { A <-> C <-> D <-> B }
		 *                     ↑
		 *                    tail (evalContext._sources)
		 */
		if(node.nextSource != null) {
			node.nextSource!!.prevSource = node.prevSource

			if(node.prevSource != null) {
				node.prevSource!!.nextSource = node.nextSource
			}

			when(context) {
				is Computed<*> -> {
					node.prevSource = context.sources
					node.nextSource = null
					context.sources!!.nextSource = node
					context.sources = node
				}

				is Effect -> {
					node.prevSource = context.sources
					node.nextSource = null
					context.sources!!.nextSource = node
					context.sources = node
				}
			}
		}
		// We can assume that the currently evaluated effect / computed signal is already
		// subscribed to change notifications from `signal` if needed.
		return node
	}
	return null
}

/**
 * The base class for plain and computed signals.
 */
open class Signal<T>(
	val name: String? = null,
	val watched: ((Signal<T>) -> Unit)? = null,
	val unwatched: ((Signal<T>) -> Unit)? = null,
	value: T,
) : ReadonlySignal<T> {

	@Suppress("UNCHECKED_CAST")
	internal open var internalValue: Any? = value

	/**
	 * @internal
	 * Version numbers should always be >= 0, because the special value -1 is used
	 * by Nodes to signify potentially unused but recyclable nodes.
	 */
	internal var version: Int = 0
	internal var node: Node? = null
	internal var targets: Node? = null
	override val brand: String = BRAND_SYMBOL

	internal open fun refresh(): Boolean {
		return true
	}

	internal open fun subscribe(node: Node) {
		val targets = this.targets
		if(targets !== node && node.prevTarget == null) {
			node.nextTarget = targets
			this.targets = node

			if(targets != null) {
				targets.prevTarget = node
			} else {
				untracked {
					this.watched?.invoke(this)
				}
			}
		}
	}

	internal open fun unsubscribe(node: Node) {
		// Only run the unsubscribe step if the signal has any subscribers to begin with.
		if(this.targets != null) {
			val prev = node.prevTarget
			val next = node.nextTarget
			if(prev != null) {
				prev.nextTarget = next
				node.prevTarget = null
			}

			if(next != null) {
				next.prevTarget = prev
				node.nextTarget = null
			}

			if(node === this.targets) {
				this.targets = next
				if(next == null) {
					untracked {
						this.unwatched?.invoke(this)
					}
				}
			}
		}
	}

	override fun subscribe(fn: (value: T) -> Unit): AutoCloseable {
		return effect(name = "sub") {
			val value = this.value
			val prevContext = evalContext
			evalContext = null
			try {
				fn(value)
			} finally {
				evalContext = prevContext
			}
		}
	}

	override fun valueOf(): T {
		return this.value
	}

	override fun toString(): String {
		return this.value.toString()
	}

	override fun toJSON(): T {
		return this.value
	}

	override fun peek(): T {
		val prevContext = evalContext
		evalContext = null
		try {
			return this.value
		} finally {
			evalContext = prevContext
		}
	}

	@Suppress("UNCHECKED_CAST")
	override var value: T
		get() {
			val node = addDependency(this)
			if(node != null) {
				node.version = this.version
			}
			return this.internalValue as T
		}
		set(value) {
			if(value !== this.internalValue) {
				if(batchIteration > 100) {
					throw Error("Cycle detected")
				}

				this.internalValue = value
				this.version++
				globalVersion++

				startBatch()
				try {
					var node = this.targets
					while(node != null) {
						when(val target = node.target) {
							is Computed<*> -> target.notify()
							is Effect -> target.notify()
						}
						node = node.nextTarget
					}
				} finally {
					endBatch()
				}
			}
		}

	operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
		this.value = value
	}
}

/**
 * Create a new plain signal.
 *
 * @param value The initial value for the signal.
 * @returns A new signal.
 */
fun <T> signal(
	value: T,
	name: String? = null,
	watched: ((Signal<T>) -> Unit)? = null,
	unwatched: ((Signal<T>) -> Unit)? = null,
): Signal<T> {
	return Signal(name, watched, unwatched, value)
}

private fun needsToRecompute(target: Any): Boolean { // Computed or Effect
	// Check the dependencies for changed values. The dependency list is already
	// in order of use. Therefore if multiple dependencies have changed values, only
	// the first used dependency is re-evaluated at this point.
	val sources = when(target) {
		is Computed<*> -> target.sources
		is Effect -> target.sources
		else -> null
	}
	var node = sources
	while(node != null) {
		if(
		// If the dependency has definitely been updated since its version number
		// was observed, then we need to recompute. This first check is not strictly
		// necessary for correctness, but allows us to skip the refresh call if the
		// dependency has already been updated.
			node.source!!.version != node.version ||
			// Refresh the dependency. If there's something blocking the refresh (e.g. a
			// dependency cycle), then we need to recompute.
			!node.source!!.refresh() ||
			// If the dependency got a new version after the refresh, then we need to recompute.
			node.source!!.version != node.version
		) {
			return true
		}
		node = node.nextSource
	}
	// If none of the dependencies have changed values since last recompute then
	// there's no need to recompute.
	return false
}

private fun prepareSources(target: Any) { // Computed or Effect
	/**
	 * 1. Mark all current sources as re-usable nodes (version: -1)
	 * 2. Set a rollback node if the current node is being used in a different context
	 * 3. Point 'target._sources' to the tail of the doubly-linked list, e.g:
	 *
	 *    { undefined <- A <-> B <-> C -> undefined }
	 *                   ↑           ↑
	 *                   │           └──────┐
	 * target._sources = A; (node is head)  │
	 *                   ↓                  │
	 * target._sources = C; (node is tail) ─┘
	 */
	val sources = when(target) {
		is Computed<*> -> target.sources
		is Effect -> target.sources
		else -> null
	}
	var node = sources
	while(node != null) {
		val rollbackNode = node.source!!.node
		if(rollbackNode != null) {
			node.rollbackNode = rollbackNode
		}
		node.source!!.node = node
		node.version = -1

		if(node.nextSource == null) {
			when(target) {
				is Computed<*> -> target.sources = node
				is Effect -> target.sources = node
			}
			break
		}
		node = node.nextSource
	}
}

private fun cleanupSources(target: Any) { // Computed or Effect
	val sources = when(target) {
		is Computed<*> -> target.sources
		is Effect -> target.sources
		else -> null
	}
	var node = sources
	var head: Node? = null
	/**
	 * At this point 'target._sources' points to the tail of the doubly-linked list.
	 * It contains all existing sources + new sources in order of use.
	 * Iterate backwards until we find the head node while dropping old dependencies.
	 */
	while(node != null) {
		val prev = node.prevSource
		/**
		 * The node was not re-used, unsubscribe from its change notifications and remove itself
		 * from the doubly-linked list. e.g:
		 *
		 * { A <-> B <-> C }
		 *         ↓
		 *    { A <-> C }
		 */
		if(node.version == -1) {
			node.source!!.unsubscribe(node)

			if(prev != null) {
				prev.nextSource = node.nextSource
			}
			if(node.nextSource != null) {
				node.nextSource!!.prevSource = prev
			}
		} else {
			/**
			 * The new head is the last node seen which wasn't removed/unsubscribed
			 * from the doubly-linked list. e.g:
			 *
			 * { A <-> B <-> C }
			 *   ↑     ↑     ↑
			 *   │     │     └ head = node
			 *   │     └ head = node
			 *   └ head = node
			 */
			head = node
		}

		node.source!!.node = node.rollbackNode
		if(node.rollbackNode != null) {
			node.rollbackNode = null
		}

		node = prev
	}

	when(target) {
		is Computed<*> -> target.sources = head
		is Effect -> target.sources = head
	}
}

/**
 * An interface for read-only signals.
 */
interface ReadonlySignal<T> {

	val value: T
	fun peek(): T
	fun subscribe(fn: (value: T) -> Unit): AutoCloseable
	fun valueOf(): T
	override fun toString(): String
	fun toJSON(): T
	val brand: String

	operator fun getValue(thisRef: Any?, property: KProperty<*>) = value
}

/**
 * The base class for computed signals.
 */
class Computed<T> internal constructor(
	name: String? = null,
	watched: ((Signal<T>) -> Unit)? = null,
	unwatched: ((Signal<T>) -> Unit)? = null,
	val fn: () -> T,
) : Signal<T>(name, watched, unwatched, null as T), ReadonlySignal<T> {

	internal var sources: Node? = null
	internal var globalVersion: Int = com.greentree.commons.signals.globalVersion - 1
	internal var flags: Int = OUTDATED

	internal override fun refresh(): Boolean {
		this.flags = this.flags and NOTIFIED.inv()

		if((this.flags and RUNNING) != 0) {
			return false
		}
		// If this computed signal has subscribed to updates from its dependencies
		// (TRACKING flag set) and none of them have notified about changes (OUTDATED
		// flag not set), then the computed value can't have changed.
		if((this.flags and (OUTDATED or TRACKING)) == TRACKING) {
			return true
		}
		this.flags = this.flags and OUTDATED.inv()

		if(this.globalVersion == com.greentree.commons.signals.globalVersion) {
			return true
		}
		this.globalVersion = com.greentree.commons.signals.globalVersion
		// Mark this computed signal running before checking the dependencies for value
		// changes, so that the RUNNING flag can be used to notice cyclical dependencies.
		this.flags = this.flags or RUNNING
		if(this.version > 0 && !needsToRecompute(this)) {
			this.flags = this.flags and RUNNING.inv()
			return true
		}
		val prevContext = evalContext
		try {
			prepareSources(this)
			evalContext = this
			val value = this.fn()
			if(
				(this.flags and HAS_ERROR) != 0 ||
				this.internalValue !== value ||
				this.version == 0
			) {
				this.internalValue = value
				this.flags = this.flags and HAS_ERROR.inv()
				this.version++
			}
		} catch(err: Throwable) {
			this.internalValue = err
			this.flags = this.flags or HAS_ERROR
			this.version++
		}
		evalContext = prevContext
		cleanupSources(this)
		this.flags = this.flags and RUNNING.inv()
		return true
	}

	internal override fun subscribe(node: Node) {
		if(this.targets == null) {
			this.flags = this.flags or OUTDATED or TRACKING
			// A computed signal subscribes lazily to its dependencies when it
			// gets its first subscriber.
			var sourceNode = this.sources
			while(sourceNode != null) {
				sourceNode.source!!.subscribe(sourceNode)
				sourceNode = sourceNode.nextSource
			}
		}
		super.subscribe(node)
	}

	internal override fun unsubscribe(node: Node) {
		// Only run the unsubscribe step if the computed signal has any subscribers.
		if(this.targets != null) {
			super.unsubscribe(node)
			// Computed signal unsubscribes from its dependencies when it loses its last subscriber.
			// This makes it possible for unreferences subgraphs of computed signals to get garbage collected.
			if(this.targets == null) {
				this.flags = this.flags and TRACKING.inv()
				var sourceNode = this.sources
				while(sourceNode != null) {
					sourceNode.source!!.unsubscribe(sourceNode)
					sourceNode = sourceNode.nextSource
				}
			}
		}
	}

	internal fun notify() {
		if((this.flags and NOTIFIED) == 0) {
			this.flags = this.flags or OUTDATED or NOTIFIED
			var node = this.targets
			while(node != null) {
				when(val target = node.target) {
					is Computed<*> -> target.notify()
					is Effect -> target.notify()
				}
				node = node.nextTarget
			}
		}
	}

	@Suppress("UNCHECKED_CAST")
	override var value: T
		get() {
			if((this.flags and RUNNING) != 0) {
				throw Error("Cycle detected")
			}
			val node = addDependency(this)
			this.refresh()
			if(node != null) {
				node.version = this.version
			}
			if((this.flags and HAS_ERROR) != 0) {
				throw this.internalValue as Throwable
			}
			return this.internalValue as T
		}
		set(_) {
			// Computed signals are read-only
			throw UnsupportedOperationException("Cannot set value on computed signal")
		}
}

/**
 * Create a new signal that is computed based on the values of other signals.
 *
 * The returned computed signal is read-only, and its value is automatically
 * updated when any signals accessed from within the callback function change.
 *
 * @param fn The effect callback.
 * @returns A new read-only signal.
 */
fun <T> computed(
	name: String? = null,
	watched: ((Signal<T>) -> Unit)? = null,
	unwatched: ((Signal<T>) -> Unit)? = null,
	fn: () -> T,
): ReadonlySignal<T> {
	return Computed(name, watched, unwatched, fn)
}

private fun cleanupEffect(effect: Effect) {
	val cleanup = effect.cleanup
	effect.cleanup = null

	if(cleanup != null) {
		startBatch()
		// Run cleanup functions always outside of any context.
		val prevContext = evalContext
		evalContext = null
		try {
			cleanup.close()
		} catch(err: Throwable) {
			effect.flags = effect.flags and RUNNING.inv()
			effect.flags = effect.flags or DISPOSED
			disposeEffect(effect)
			throw err
		} finally {
			evalContext = prevContext
			endBatch()
		}
	}
}

private fun disposeEffect(effect: Effect) {
	var node = effect.sources
	while(node != null) {
		node.source!!.unsubscribe(node)
		node = node.nextSource
	}
	effect.fn = null
	effect.sources = null

	cleanupEffect(effect)
}

private fun endEffect(effect: Effect, prevContext: Any?) {
	if(evalContext !== effect) {
		throw Error("Out-of-order effect")
	}
	cleanupSources(effect)
	evalContext = prevContext

	effect.flags = effect.flags and RUNNING.inv()
	if((effect.flags and DISPOSED) != 0) {
		disposeEffect(effect)
	}
	endBatch()
}

/**
 * The base class for reactive effects.
 */
class Effect internal constructor(
	val name: String? = null,
	var fn: (() -> (AutoCloseable))? = null,
) : AutoCloseable {

	internal var cleanup: AutoCloseable? = null
	internal var sources: Node? = null
	internal var nextBatchedEffect: Effect? = null
	internal var flags: Int = TRACKING

	internal fun callback() {
		val finish = this.start()
		try {
			if((this.flags and DISPOSED) != 0) return
			if(this.fn == null) return
			val cleanup = this.fn!!()
			this.cleanup = cleanup
		} finally {
			finish()
		}
	}

	internal fun start(): () -> Unit {
		if((this.flags and RUNNING) != 0) {
			throw Error("Cycle detected")
		}
		this.flags = this.flags or RUNNING
		this.flags = this.flags and DISPOSED.inv()
		cleanupEffect(this)
		prepareSources(this)

		startBatch()
		val prevContext = evalContext
		evalContext = this
		return { endEffect(this, prevContext) }
	}

	internal fun notify() {
		if((this.flags and NOTIFIED) == 0) {
			this.flags = this.flags or NOTIFIED
			this.nextBatchedEffect = batchedEffect
			batchedEffect = this
		}
	}

	override fun close() {
		this.flags = this.flags or DISPOSED

		if((this.flags and RUNNING) == 0) {
			disposeEffect(this)
		}
	}
}

/**
 * Create an effect to run arbitrary code in response to signal changes.
 *
 * An effect tracks which signals are accessed within the given callback
 * function `fn`, and re-runs the callback when those signals change.
 *
 * The callback may return a cleanup function. The cleanup function gets
 * run once, either when the callback is next called or when the effect
 * gets disposed, whichever happens first.
 *
 * @param fn The effect callback.
 * @returns A function for disposing the effect.
 */
fun effectClose(name: String? = null, fn: () -> (AutoCloseable)): AutoCloseable {
	val effect = Effect(name, fn)
	try {
		effect.callback()
	} catch(err: Throwable) {
		effect.close()
		throw err
	}
	// Return a bound function instead of a wrapper like `() => effect._dispose()`,
	// because bound functions seem to be just as fast and take up a lot less memory.
	return effect
}

fun effect(name: String? = null, fn: () -> Unit) = effectClose(name) {
	fn()
	EmptyAutoCloseable
}

