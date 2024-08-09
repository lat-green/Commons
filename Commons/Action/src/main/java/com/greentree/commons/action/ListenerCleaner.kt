package com.greentree.commons.action

import java.lang.ref.Cleaner

object ListenerCleaner {

	private val cleaner = Cleaner.create()

	fun register(thisRef: Any, listenerCloser: ListenerCloser) {
		cleaner.register(thisRef, CloseRunnable(listenerCloser))
	}
}

fun ListenerCloser.closeOnFinalize(thisRef: Any) {
	return ListenerCleaner.register(thisRef, this)
}