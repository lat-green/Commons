package com.greentree.commons.action.property

import com.greentree.commons.action.ListenerCloser
import com.greentree.commons.action.observable.RunObservable

interface ReactiveProperty<out T : Any> : RunObservable {

	val value: T
}

fun <T : Any> ReactiveProperty<T>.onNewValue(onNewValue: (T) -> Unit): ListenerCloser {
	return addListener {
		onNewValue(value)
	}
}