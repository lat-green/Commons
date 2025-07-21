package com.greentree.commons.react

fun <T> ReactContext.usePreviousNotEquals(value: T): T? {
	var currentRef by useRef(value)
	var previousRef by useRef<T>()
	if(currentRef != value) {
		previousRef = currentRef
		currentRef = value
	}
	return previousRef
}

fun <T> ReactContext.usePrevious(value: T): T? {
	var previous by useRef<T>()
	val result = previous
	previous = value
	return result
}
