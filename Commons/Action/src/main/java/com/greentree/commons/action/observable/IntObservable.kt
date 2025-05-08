package com.greentree.commons.action.observable

import com.greentree.commons.action.ListenerCloser
import java.util.function.IntConsumer

interface IntObservable : RunObservable {

	override fun addListener(listener: Runnable) = addListener { _ -> listener.run() }

	fun addListener(listener: IntConsumer): ListenerCloser

	data object Empty : IntObservable {

		override val listenerCount: Int
			get() = 0

		override fun addListener(listener: IntConsumer): ListenerCloser {
			return ListenerCloser.Empty
		}

		override fun close() {
		}
	}
}
