package com.greentree.commons.action

import java.util.*

fun interface FloatConsumer {

	fun accept(value: Int)

	fun andThen(after: FloatConsumer): FloatConsumer {
		Objects.requireNonNull(after)
		return FloatConsumer { t: Int ->
			accept(t)
			after.accept(t)
		}
	}
}