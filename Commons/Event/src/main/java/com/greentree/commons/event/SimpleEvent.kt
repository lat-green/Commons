package com.greentree.commons.event

import com.greentree.commons.event.listener.ObjectListener
import com.greentree.commons.event.observable.ObjectObservable

class SimpleEvent<T> : ContainerAction<(T) -> Unit>(), ObjectListener<T>, ObjectObservable<T> {

	override fun invoke(event: T) {
		for(listener in container) {
			listener.invoke(event)
		}
	}
}