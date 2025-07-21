package com.greentree.commons.action.react

import com.greentree.commons.action.observable.RunObservable
import com.greentree.commons.react.ReactContext
import com.greentree.commons.react.useEffectClose

fun ReactContext.refreshOnEvent(action: RunObservable) = useEffectClose(action) {
	action.addListener {
		refresh()
	}
}
