package com.greentree.commons.react

import kotlin.reflect.KProperty

interface Ref<T> {

	var value: T
}

operator fun <T> Ref<T>.getValue(thisRef: Any?, property: KProperty<*>): T = value
operator fun <T> Ref<T>.setValue(thisRef: Any?, property: KProperty<*>, value: T) {
	this.value = value
}
