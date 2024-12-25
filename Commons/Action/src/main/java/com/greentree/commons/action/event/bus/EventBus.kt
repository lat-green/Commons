package com.greentree.commons.action.event.bus

import com.greentree.commons.action.ListenerCloser
import kotlin.reflect.KClass

interface EventBus<T> {

	fun <K> topic(keyExtractor: (T) -> K): Topic<K, T>

	fun event(event: T)

	interface Topic<K, T> {

		fun addListener(key: K, listener: (T) -> Unit): ListenerCloser
	}
}

inline fun <E : Any, reified T : E> EventBus.Topic<KClass<out E>, E>.addListener(noinline listener: (T) -> Unit) =
	addListener(T::class) {
		listener(it as T)
	}