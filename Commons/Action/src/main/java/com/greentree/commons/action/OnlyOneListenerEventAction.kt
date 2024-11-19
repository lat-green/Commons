package com.greentree.commons.action

import com.greentree.commons.action.observable.ObjectObservable
import com.greentree.commons.action.observer.`object`.EventAction
import java.util.function.Consumer

data class OnlyOneListenerEventAction<T>(
	val origin: ObjectObservable<T>,
) : ObjectObservable<T> {

	private var originCloser: ListenerCloser? = null
	private val action = EventAction<T>()

	override fun addListener(listener: Consumer<in T>): ListenerCloser {
		if(originCloser == null) {
			originCloser = origin.addListener { it ->
				action.event(it)
			}
		}
		val lc = action.addListener(listener)
		return ListenerCloser {
			lc.close()
			if(action.isEmpty) {
				originCloser!!.close()
				originCloser = null
			}
		}
	}
}