package com.greentree.commons.math.vector

import com.greentree.commons.action.ListenerCloser
import com.greentree.commons.action.observable.ObjectObservable
import com.greentree.commons.action.observer.`object`.EventAction
import com.greentree.commons.math.Mathf
import java.io.Serializable

class ActionFloat(private var value: Float = 0f) : ObjectObservable<Float>, Serializable {

	private val action = EventAction<Float>()

	fun get(): Float {
		return value
	}

	fun set(value: Float) {
		if(Mathf.equals(value, this.value))
			return
		action.event(value)
		this.value = value
	}

	companion object {

		private const val serialVersionUID = 1L
	}

	override fun addListener(listener: (Float) -> Unit): ListenerCloser {
		return action.addListener(listener)
	}
}