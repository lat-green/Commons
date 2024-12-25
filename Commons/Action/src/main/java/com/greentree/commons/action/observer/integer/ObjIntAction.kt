package com.greentree.commons.action.observer.integer

import com.greentree.commons.action.MultiAction
import com.greentree.commons.action.observable.ObjIntObservable
import com.greentree.commons.action.observer.ObjIntObserver
import java.util.function.ObjIntConsumer

class ObjIntAction<T> : MultiAction<ObjIntConsumer<in T>>(), ObjIntObservable<T>, ObjIntObserver<T> {

	override val listenerCount: Int
		get() = listeners.size()

	override fun event(e: T, i: Int) {
		for(l in listeners)
			l.accept(e, i)
	}
}