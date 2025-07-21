package com.greentree.commons.react

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

fun interface ChildrenReactContext<K> {

	fun useChild(id: K): ReactContext
}

fun <K> ReactContext.useChildren(): ChildrenReactContext<K> = run {
	var children by useRef(mutableMapOf<K, ReactContextProvider>()) {
		for(child in it.values) {
			child.close()
		}
		it.clear()
	}
	var current by useRef(mutableMapOf<K, ReactContextProvider>()) {
		for(child in it.values) {
			child.close()
		}
		it.clear()
	}
	children.filterNot { (key, value) ->
		current.containsKey(key)
	}.forEach { (key, value) ->
		children.remove(key)
		value.close()
	}
	current.clear()
	val parent = this
	return ChildrenReactContext {
		val child = current.getOrPut(it) {
			children.getOrPut(it) {
				FlagReactContextProvider().withRefresh {
					parent.refresh()
				}
			}
		}
		child.next()
	}
}

inline fun <T : Any> ReactContext.useForEach(sequence: Sequence<T>, block: ReactContext.(T) -> Unit) {
	val children = useChildren<T>()
	for(t in sequence)
		children.useChild(t).block(t)
}

inline fun <T : Any> ReactContext.useForEach(iterable: Iterable<T>, block: ReactContext.(T) -> Unit) {
	val children = useChildren<T>()
	for(t in iterable)
		children.useChild(t).block(t)
}

enum class IfBlock { THEN, ELSE }

@OptIn(ExperimentalContracts::class)
inline fun <R> ReactContext.useIf(value: Boolean, then: ReactContext.() -> R, `else`: ReactContext.() -> R): R {
	contract {
		callsInPlace(then, InvocationKind.AT_MOST_ONCE)
		callsInPlace(`else`, InvocationKind.AT_MOST_ONCE)
	}
	val children = useChildren<IfBlock>()
	val thenBlock = children.useChild(IfBlock.THEN)
	val elseBlock = children.useChild(IfBlock.ELSE)
	return if(value)
		thenBlock.run(then)
	else
		elseBlock.run(`else`)
}