package com.greentree.commons.action.property

import com.greentree.commons.action.ListenerCloser

data class ConstReactiveProperty<T : Any>(
	override val value: T,
) : ReactiveProperty<T> {

	override fun addListener(listener: Runnable): ListenerCloser {
		return ListenerCloser.Empty
	}

	override fun close() {
	}
}