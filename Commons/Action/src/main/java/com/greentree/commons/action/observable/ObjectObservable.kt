package com.greentree.commons.action.observable

import com.greentree.commons.action.ListenerCloser
import java.util.function.Consumer

interface ObjectObservable<T> : RunObservable {

	override fun addListener(listener: Runnable) = addListener { _ -> listener.run() }

	fun addListener(listener: (T) -> Unit): ListenerCloser

	data object Empty : ObjectObservable<Any?> {

		fun <T> instance() = Empty as ObjectObservable<T>

		override val listenerCount: Int
			get() = 0

		override fun addListener(listener: (Any?) -> Unit): ListenerCloser {
			return ListenerCloser.Empty
		}
	}
}

@Deprecated("", ReplaceWith("addListener { listener.accept(it) }"))
fun <T> ObjectObservable<T>.addListener(listener: Consumer<in T>): ListenerCloser = addListener { listener.accept(it) }