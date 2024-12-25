package com.greentree.commons.action.observable

import com.greentree.commons.action.ListenerCloser
import java.util.function.IntConsumer
import java.util.function.ObjIntConsumer

interface ObjIntObservable<T> : RunObservable, IntObservable, ObjectObservable<T> {

	override fun addListener(listener: Runnable): ListenerCloser {
		return addListener { _, _ -> listener.run() }
	}

	fun addListener(listener: ObjIntConsumer<in T>): ListenerCloser

	override fun addListener(listener: (T) -> Unit): ListenerCloser {
		return addListener { e: T, _ -> listener.invoke(e) }
	}

	override fun addListener(listener: IntConsumer): ListenerCloser {
		return addListener { _, i: Int -> listener.accept(i) }
	}

	data object Empty : ObjIntObservable<Any?> {

		fun <T> instance() = Empty as ObjIntObservable<T>

		override val listenerCount: Int
			get() = 0

		override fun addListener(listener: ObjIntConsumer<in Any?>): ListenerCloser {
			return ListenerCloser.Empty
		}
	}
}
