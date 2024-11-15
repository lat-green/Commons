package com.greentree.commons.action.react

import com.greentree.commons.action.observable.RunObservable
import com.greentree.commons.util.react.REACT
import com.greentree.commons.util.react.ReactContext
import com.greentree.commons.util.react.useEffectClose

fun refreshOnEvent(action: RunObservable) = REACT.get().refreshOnEvent(action)
fun ReactContext.refreshOnEvent(action: RunObservable) = useEffectClose(action) {
	action.addListener {
		refresh()
	}
}
