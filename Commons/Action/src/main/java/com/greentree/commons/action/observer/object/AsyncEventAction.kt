package com.greentree.commons.action.observer.`object`

import java.util.concurrent.ExecutionException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Future

/** @author Arseny Latyshev
 */
class AsyncEventAction<T>(private val executor: ExecutorService) : EventAction<T>() {

	override fun event(t: T) {
		val futures: ArrayList<Future<*>> = ArrayList<Future<*>>(listeners.size())
		for(l in listeners) {
			val f: Future<*> = executor.submit {
				l(t)
			}
			futures.add(f)
		}
		for(f in futures) {
			try {
				f.get()
			} catch(e: InterruptedException) {
				e.printStackTrace()
			} catch(e: ExecutionException) {
				e.printStackTrace()
			}
		}
	}

	companion object {

		private const val serialVersionUID = 1L
	}
}
