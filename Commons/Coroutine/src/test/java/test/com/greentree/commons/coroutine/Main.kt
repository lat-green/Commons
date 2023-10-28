package test.com.greentree.commons.coroutine

import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.concurrent.Executors

class Main {

	@Test
	fun main1() {
		val fibonacciSeq = sequence {
			var a = 0
			var b = 1

			yield(1)

			while(true) {
				yield(a + b)
				val tmp = a + b
				a = b
				b = tmp
			}
		}
		assertEquals(fibonacciSeq.take(5).toList(), listOf(1, 1, 2, 3, 5))
	}

	@Test
	fun main2() = runBlocking(Executors.newVirtualThreadPerTaskExecutor().asCoroutineDispatcher()) {
		println("main ${Thread.currentThread()}")
		val a = launch {
			println(Thread.currentThread())
			delay(1000)
		}
		val b = launch {
			println(Thread.currentThread())
			delay(1000)
		}
		a.join()
		b.join()
	}
}
