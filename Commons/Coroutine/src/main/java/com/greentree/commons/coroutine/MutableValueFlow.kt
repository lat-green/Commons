package com.greentree.commons.coroutine

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

class MutableValueFlow<T> private constructor() : Flow<T>, AutoCloseable {

	val channel = Channel<T>(Channel.CONFLATED)

	init {
		println(channel)
	}

	suspend fun setValue(value: T) {
		channel.send(value)
	}

	override suspend fun collect(collector: FlowCollector<T>) {
		for(x in channel)
			collector.emit(x)
	}

	override fun close() {
		channel.close()
	}

	companion object {

		suspend fun <T> create(value: T): MutableValueFlow<T> {
			val v = MutableValueFlow<T>()
			v.setValue(value)
			return v
		}
	}
}

suspend fun <T> MutableFlow(value: T): MutableValueFlow<T> {
	return MutableValueFlow.create(value)
}