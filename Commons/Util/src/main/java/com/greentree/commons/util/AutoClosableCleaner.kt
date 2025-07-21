package com.greentree.commons.util

import java.lang.ref.Cleaner

data object AutoClosableCleaner {

	private val cleaner = Cleaner.create()

	fun register(thisRef: Any?, closeable: AutoCloseable) {
		cleaner.register(thisRef, CloseRunnable(closeable))
	}
}

data class CloseRunnable(
	val autoCloseable: AutoCloseable,
) : Runnable {

	override fun run() {
		autoCloseable.close()
	}
}

fun AutoCloseable.closeOnFinalize(thisRef: Any) {
	return AutoClosableCleaner.register(thisRef, this)
}