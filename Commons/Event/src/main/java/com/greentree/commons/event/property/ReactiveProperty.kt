package com.greentree.commons.event.property

import com.greentree.commons.event.ListenerCloser
import com.greentree.commons.event.observable.RunObservable

interface ReactiveProperty<out T : Any> : RunObservable {

	val value: T
}

fun <T : Any> ReactiveProperty<T>.onNewValue(onNewValue: (T) -> Unit): ListenerCloser {
	return addListener {
		onNewValue(value)
	}
}