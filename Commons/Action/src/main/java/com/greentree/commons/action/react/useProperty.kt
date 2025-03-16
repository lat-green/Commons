package com.greentree.commons.action.react

import com.greentree.commons.action.property.ReactiveProperty
import com.greentree.commons.util.react.ReactContext

fun <T : Any> ReactContext.useProperty(property: ReactiveProperty<T>): T {
	refreshOnEvent(property)
	return property.value
}
