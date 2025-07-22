package com.greentree.commons.event.listener

import java.util.function.Consumer

fun interface ObjectListener<T> : (T) -> Unit, Consumer<T> {

	override fun invoke(event: T)

	override fun accept(t: T) = invoke(t)
}