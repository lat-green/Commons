package com.greentree.commons.event

import com.greentree.commons.event.listener.EventListener
import com.greentree.commons.event.observable.RunObservable

class RunEvent : ContainerAction<() -> Unit>(), EventListener, RunObservable {

	override fun invoke() {
		for(listener in container) {
			listener.invoke()
		}
	}
}

