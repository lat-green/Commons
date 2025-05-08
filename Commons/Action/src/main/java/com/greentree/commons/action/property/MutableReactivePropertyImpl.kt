package com.greentree.commons.action.property

import com.greentree.commons.action.ListenerCloser
import com.greentree.commons.action.observer.run.RunAction

class MutableReactivePropertyImpl<T : Any>(
	initValue: T
) : MutableReactiveProperty<T> {

	private val action = RunAction()
	override var value: T = initValue
		set(newValue) {
			field = newValue
			action.event()
		}

	override fun addListener(listener: Runnable): ListenerCloser {
		return action.addListener(listener)
	}

	override fun close() {
		action.close()
	}
}

