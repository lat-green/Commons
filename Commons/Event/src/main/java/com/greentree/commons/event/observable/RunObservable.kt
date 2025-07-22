package com.greentree.commons.event.observable

import com.greentree.commons.event.ListenerCloser
import java.io.Serializable

interface RunObservable : AutoCloseable, Serializable {

	fun addListener(listener: () -> Unit): ListenerCloser

	data object Empty : RunObservable {

		private fun readResolve(): Any = Empty

		override fun addListener(listener: () -> Unit) = ListenerCloser.Empty

		override fun close() {
		}
	}

	companion object {

		@Deprecated("use object", ReplaceWith("RunObservable.Empty"))
		@JvmField
		val NULL = Empty
	}
}

@Deprecated("", ReplaceWith("addListener { listener.run() }"))
fun RunObservable.addListener(listener: Runnable): ListenerCloser = addListener { listener.run() }
