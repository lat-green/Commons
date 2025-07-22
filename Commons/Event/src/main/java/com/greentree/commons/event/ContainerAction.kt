package com.greentree.commons.event

import com.greentree.commons.event.container.ListenerContainer
import com.greentree.commons.event.container.MultiContainer

open class ContainerAction<L>(
	val container: ListenerContainer<L> = MultiContainer()
) : AutoCloseable {

	fun addListener(listener: L): ListenerCloser {
		return container.add(listener)
	}

	override fun close() {
		container.clear()
	}
}
