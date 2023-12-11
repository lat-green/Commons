package com.greentree.commons.action.event.bus

interface EventBus<K, L> {

	fun topic(key: K): Topic<L>

	interface Topic<L> {

		fun addListener(listener: L)
		fun removeListener(listener: L)

		fun event(block: (L) -> Unit)
	}

	companion object {

		fun <K> newSimpleEventBus() = EventBusImpl<K, () -> Unit>()
		fun newClassEventBus() = EventBusImpl<Class<*>, (Any) -> Unit>()
	}
}

fun <K, L> EventBus<K, L>.addListener(key: K, listener: L) = topic(key).addListener(listener)
fun <K, L> EventBus<K, L>.removeListener(key: K, listener: L) = topic(key).removeListener(listener)

fun <K, L> EventBus<K, L>.event(key: K, block: (L) -> Unit) = topic(key).event(block)

fun <K> EventBus<K, () -> Unit>.event(key: K) = topic(key).event { it() }

inline fun <reified T> EventBusImpl<Class<*>, (Any) -> Unit>.addListener(noinline block: (T) -> Unit) =
	topic(T::class.java).addListener {
		if(it is T)
			block(it)
	}

inline fun <reified T : Any> EventBusImpl<Class<*>, (Any) -> Unit>.event(argument: T) =
	topic(T::class.java).event { it(argument) }

fun <K, A, L : (A) -> Unit> EventBus<K, L>.event(key: K, argument: A) = topic(key).event { it(argument) }