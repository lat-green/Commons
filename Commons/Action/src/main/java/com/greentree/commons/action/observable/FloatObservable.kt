package com.greentree.commons.action.observable

import com.greentree.commons.action.FloatConsumer
import com.greentree.commons.action.ListenerCloser

interface FloatObservable : RunObservable {

	override fun addListener(listener: Runnable) = addListener { _ -> listener.run() }

	fun addListener(listener: FloatConsumer): ListenerCloser

	data object Empty : FloatObservable {

		override val listenerCount: Int
			get() = 0

		override fun addListener(listener: FloatConsumer): ListenerCloser {
			return ListenerCloser.Empty
		}

		override fun close() {
		}
	}
}
