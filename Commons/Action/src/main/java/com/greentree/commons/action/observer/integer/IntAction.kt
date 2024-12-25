package com.greentree.commons.action.observer.integer

import com.greentree.commons.action.MultiAction
import com.greentree.commons.action.observable.IntObservable
import com.greentree.commons.action.observer.IntObserver
import java.util.function.IntConsumer

class IntAction : MultiAction<IntConsumer>(), IntObserver, IntObservable {

	override val listenerCount: Int
		get() = listeners.size()

	override fun event(i: Int) {
		for(l in listeners)
			l.accept(i)
	}
}
