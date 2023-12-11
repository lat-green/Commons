package com.greentree.commons.action.event.bus

interface EventBus<K, A> {

	fun topic(key: K): Topic<A>

	interface Topic<A> {

		fun addListener(listener: (A) -> Unit)
		fun removeListener(listener: (A) -> Unit)

		fun event(argument: A)
	}
}

fun <K, A> EventBus<K, A>.addListener(key: K, listener: (A) -> Unit) = topic(key).addListener(listener)
fun <K, A> EventBus<K, A>.removeListener(key: K, listener: (A) -> Unit) = topic(key).removeListener(listener)

fun <K, A> EventBus<K, A>.event(key: K, argument: A) = topic(key).event(argument)