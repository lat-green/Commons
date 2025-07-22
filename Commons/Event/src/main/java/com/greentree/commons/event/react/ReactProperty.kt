package com.greentree.commons.event.react

import com.greentree.commons.event.ListenerCloser
import com.greentree.commons.event.RunEvent
import com.greentree.commons.event.property.ReactiveProperty
import com.greentree.commons.react.EventReactRunner
import com.greentree.commons.react.ReactContext

data class ReactProperty<T : Any>(
	val runContext: ReactContext.() -> T,
) : ReactiveProperty<T> {

	private val action = RunEvent()
	private val runner = EventReactRunner(runContext) {
		action()
	}
	override val value: T
		get() = runner.runReact()

	override fun addListener(listener: () -> Unit): ListenerCloser {
		return action.addListener(listener)
	}

	override fun close() {
		action.close()
	}
}