package com.greentree.commons.coroutine

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

suspend fun Flow<*>.isEmpty() = !isNotEmpty()
suspend fun Flow<*>.isNotEmpty() = firstOrNull() != null

suspend fun Flow<*>.size(): Int {
	var size = 0
	collect {
		size++
	}
	return size
}

suspend operator fun <T> Flow<T>.iterator() = buildList {
	collect {
		add(it)
	}
}.iterator()


