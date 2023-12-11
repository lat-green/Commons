package com.greentree.commons.action.event.bus

interface KeyedListener<K, A> : (A) -> Unit {

	val key: K
}

fun <K, A> EventBus<K, A>.addListener(listener: KeyedListener<K, A>) = topic(listener.key).addListener(listener)
fun <K, A> EventBus<K, A>.removeListener(listener: KeyedListener<K, A>) = topic(listener.key).removeListener(listener)