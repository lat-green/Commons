package com.greentree.commons.util

import java.lang.ref.Cleaner

object AutoClosableCleaner {

	private val cleaner = Cleaner.create()

	fun register(thisRef: Any, closeable: AutoCloseable) {
		cleaner.register(thisRef, CloseRunnable(closeable))
	}
}

fun AutoCloseable.closeOnFinalize(thisRef: Any) {
	return AutoClosableCleaner.register(thisRef, this)
}