package com.greentree.commons.util

data class CloseRunnable(
	val listenerCloser: AutoCloseable,
) : Runnable {

	override fun run() {
		listenerCloser.close()
	}
}
