package com.greentree.commons.event.property

import com.greentree.commons.event.ListenerCloser

data class ConstReactiveProperty<T : Any>(
	override val value: T,
) : ReactiveProperty<T> {

	override fun addListener(listener: () -> Unit) = ListenerCloser.Empty

	override fun close() {
	}
}