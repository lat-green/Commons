package com.greentree.commons.action.react

import com.greentree.commons.action.ListenerCloser
import com.greentree.commons.action.observer.run.RunAction
import com.greentree.commons.action.property.ReactiveProperty
import com.greentree.commons.util.react.EventReactContextProvider
import com.greentree.commons.util.react.ReactContext
import com.greentree.commons.util.react.runDeepReact

data class ReactProperty<T : Any>(
	val runContext: ReactContext.() -> T,
) : ReactiveProperty<T> {

	private val action = RunAction()
	private var hasChanges = false
	private val provider = EventReactContextProvider {
		hasChanges = true
		action.event()
	}
	override var value: T = newValue()
		private set
		get() {
			if(hasChanges) {
				hasChanges = false
				val newValue = newValue()
				field = newValue
			}
			return field
		}

	override fun addListener(listener: Runnable): ListenerCloser {
		return action.addListener(listener)
	}

	private fun newValue(): T {
		return provider.runDeepReact(runContext)
	}
}