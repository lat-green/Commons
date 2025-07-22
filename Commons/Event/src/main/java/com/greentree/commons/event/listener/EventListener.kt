package com.greentree.commons.event.listener

fun interface EventListener : () -> Unit, Runnable, ObjectListener<Any?> {

	override fun invoke()

	override fun invoke(event: Any?) {
		invoke()
	}

	override fun run() = invoke()
}