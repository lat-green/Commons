package com.greentree.commons.util.iterator

fun Iterable<*>.isEmpty() = !isNotEmpty()
fun Iterable<*>.isNotEmpty() = iterator().hasNext()

fun Sequence<*>.isEmpty() = !isNotEmpty()
fun Sequence<*>.isNotEmpty() = iterator().hasNext()

val Iterable<*>.size: Int
	get() {
		var size = 0
		for(e in this)
			size++
		return size
	}
val Sequence<*>.size: Int
	get() {
		var size = 0
		for(e in this)
			size++
		return size
	}
