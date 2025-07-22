package com.greentree.commons.event.observable

import com.greentree.commons.event.ListenerCloser
import java.util.function.Consumer

interface ObjectObservable<T> : RunObservable {

	override fun addListener(listener: () -> Unit) = addListener { _ -> listener() }

	fun addListener(listener: (T) -> Unit): ListenerCloser

	data object Empty : ObjectObservable<Any?> {

		private fun readResolve(): Any = Empty

		fun <T> instance() = Empty as ObjectObservable<T>

		override fun addListener(listener: (Any?) -> Unit): ListenerCloser {
			return ListenerCloser.Empty
		}

		override fun close() {
		}
	}
}

@Deprecated("", ReplaceWith("addListener { listener.accept(it) }"))
fun <T> ObjectObservable<T>.addListener(listener: Consumer<in T>): ListenerCloser =
	addListener { it -> listener.accept(it) }