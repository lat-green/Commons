package com.greentree.commons.action

fun interface FloatConsumer {

	fun accept(value: Float)

	fun andThen(after: FloatConsumer): FloatConsumer {
		return FloatConsumer { t: Float ->
			accept(t)
			after.accept(t)
		}
	}
}