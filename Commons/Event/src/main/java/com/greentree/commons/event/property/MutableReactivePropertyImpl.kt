package com.greentree.commons.event.property

import com.greentree.commons.event.ListenerCloser
import com.greentree.commons.event.RunEvent

class MutableReactivePropertyImpl<T : Any>(
	initValue: T
) : MutableReactiveProperty<T> {

	private val event = RunEvent()
	override var value: T = initValue
		set(newValue) {
			field = newValue
			event()
		}

	override fun addListener(listener: () -> Unit): ListenerCloser {
		return event.addListener(listener)
	}

	override fun close() {
		event.close()
	}
}

