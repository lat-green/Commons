package com.greentree.commons.event.react

import com.greentree.commons.event.property.ReactiveProperty
import com.greentree.commons.react.ReactContext

fun <T : Any> ReactContext.useProperty(property: ReactiveProperty<T>): T {
	refreshOnEvent(property)
	return property.value
}
