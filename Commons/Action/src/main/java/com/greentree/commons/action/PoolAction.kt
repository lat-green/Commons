package com.greentree.commons.action

import com.greentree.commons.action.observer.`object`.IObjectAction
import java.util.concurrent.CopyOnWriteArrayList

open class PoolAction<T, A : IObjectAction<T>>(val action: A) : IObjectAction<T> {

	private val pool: MutableCollection<T> = CopyOnWriteArrayList<T>()

	override fun addListener(listener: Function1<T, Unit>): ListenerCloser {
		val res: ListenerCloser = action.addListener(listener)
		for(v in pool)
			listener.invoke(v)
		return res
	}

	override fun clear() {
		clearPool()
		action.clear()
	}

	fun clearPool() {
		pool.clear()
	}

	override fun event(t: T) {
		if(pool.contains(t)) return
		pool.add(t)
		action.event(t)
	}

	override val listenerCount: Int
		get() = action.listenerCount

	override fun toString(): String {
		val builder = "PoolAction [" +
				action +
				"]"
		return builder
	}

	override fun close() {
		pool.clear()
		action.close()
	}

	companion object {

		private const val serialVersionUID = 1L
	}
}
