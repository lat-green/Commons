package com.greentree.commons.action.observable

import com.greentree.commons.action.ListenerCloser
import java.io.Serializable

interface RunObservable : AutoCloseable, Serializable {

	fun addListener(listener: Runnable): ListenerCloser

	val isEmpty: Boolean
		get() = listenerCount == 0
	val listenerCount: Int
		get() = TODO()

	data object Empty : RunObservable {

		override fun addListener(listener: Runnable): ListenerCloser {
			return ListenerCloser.Empty
		}

		override val listenerCount: Int
			get() = 0

		override fun close() {
		}
	}

	companion object {

		@Deprecated("use object", ReplaceWith("RunObservable.Empty"))
		@JvmField
		val NULL = Empty
	}
}
