package com.greentree.commons.action.react

import com.greentree.commons.action.ListenerCloser
import com.greentree.commons.action.observer.run.RunAction
import com.greentree.commons.action.property.ReactiveProperty
import com.greentree.commons.react.EventReactRunner
import com.greentree.commons.react.ReactContext

data class ReactProperty<T : Any>(
	val runContext: ReactContext.() -> T,
) : ReactiveProperty<T> {

	private val action = RunAction()
	private val runner = EventReactRunner(runContext) {
		action.event()
	}
	override val value: T
		get() = runner.runReact()

	override fun addListener(listener: Runnable): ListenerCloser {
		return action.addListener(listener)
	}

	override fun close() {
		action.close()
	}
}