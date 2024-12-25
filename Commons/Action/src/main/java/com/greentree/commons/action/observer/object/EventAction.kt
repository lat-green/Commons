package com.greentree.commons.action.observer.`object`

import com.greentree.commons.action.MultiAction

/**
 * @author Arseny Latyshev
 */
open class EventAction<T> : MultiAction<(T) -> Unit>(), IObjectAction<T> {

	override val listenerCount: Int
		get() = listeners.size()

	override fun event(t: T) {
		for(l in listeners)
			l(t)
	}
}
