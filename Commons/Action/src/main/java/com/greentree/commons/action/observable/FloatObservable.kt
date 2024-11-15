package com.greentree.commons.action.observable

import com.greentree.commons.action.ListenerCloser

interface FloatObservable : RunObservable {

	override fun addListener(listener: Runnable) = addListener { _ -> listener.run() }

	fun addListener(listener: (Float) -> Unit): ListenerCloser

	companion object {

		val NULL: FloatObservable = object : FloatObservable {
			private val serialVersionUID = 1L

			override fun listenerSize(): Int {
				return 0
			}

			override fun addListener(listener: (Float) -> Unit): ListenerCloser {
				return ListenerCloser.EMPTY
			}
		}
	}
}
