package com.greentree.commons.event.react

import com.greentree.commons.event.observable.RunObservable
import com.greentree.commons.react.ReactContext
import com.greentree.commons.react.useEffectClose

fun ReactContext.refreshOnEvent(action: RunObservable) = useEffectClose(action) {
	action.addListener {
		refresh()
	}
}
