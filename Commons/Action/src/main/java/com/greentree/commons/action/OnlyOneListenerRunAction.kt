package com.greentree.commons.action

import com.greentree.commons.action.observable.RunObservable
import com.greentree.commons.action.observer.run.RunAction

data class OnlyOneListenerRunAction(
	val origin: RunObservable,
) : RunObservable {

	private var originCloser: ListenerCloser? = null
	private val action = RunAction()

	override fun addListener(listener: Runnable): ListenerCloser {
		if(originCloser == null) {
			originCloser = origin.addListener {
				action.event()
			}
		}
		val lc = action.addListener(listener)
		return ListenerCloser {
			lc.close()
			if(origin.isEmpty) {
				originCloser!!.close()
				originCloser = null
			}
		}
	}

	override val listenerCount: Int
		get() = action.listenerCount

	override fun close() {
		action.close()
		origin.close()
	}
}