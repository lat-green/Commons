package com.greentree.commons.util.react

fun <T> ReactContext.usePreviousNotEquals(value: T): T? {
	var currentRef by useRef(value)
	var previousRef by useRef<T>()
	if(currentRef != value) {
		previousRef = currentRef
		currentRef = value
	}
	return previousRef
}

fun <T> usePreviousNotEquals(value: T): T? = REACT.get().usePreviousNotEquals(value)

fun <T> ReactContext.usePrevious(value: T): T? {
	var previous by useRef<T>()
	val result = previous
	previous = value
	return result
}

fun <T> usePrevious(value: T): T? = REACT.get().usePrevious(value)