package com.greentree.commons.action.observable

import com.greentree.commons.action.ListenerCloser

interface PairObservable<T1, T2> : RunObservable, ObjectObservable<Pair<T1, T2>> {

	override fun addListener(listener: Runnable): ListenerCloser {
		return addListener { _, _ -> listener.run() }
	}

	fun addListener(listener: (T1, T2) -> Unit): ListenerCloser

	override fun addListener(listener: (Pair<T1, T2>) -> Unit): ListenerCloser {
		return addListener { a: T1, b: T2 ->
			listener(Pair(a, b))
		}
	}

	data object Empty : PairObservable<Any?, Any?> {

		fun <T1, T2> instance() = Empty as PairObservable<T1, T2>

		override val listenerCount: Int
			get() = 0

		override fun addListener(listener: (Any?, Any?) -> Unit): ListenerCloser {
			return ListenerCloser.Empty
		}

		override fun close() {
		}
	}
}
