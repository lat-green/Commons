package com.greentree.commons.util.concurent

import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

data class DoneFuture<T>(val result: T) : Future<T> {

	override fun cancel(mayInterruptIfRunning: Boolean): Boolean {
		return false
	}

	override fun isCancelled() = false

	override fun isDone() = true

	override fun get() = result

	override fun get(timeout: Long, unit: TimeUnit) = result
}
