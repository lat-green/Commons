package com.greentree.commons.util

import org.junit.jupiter.api.Assertions.*
import java.lang.ref.WeakReference
import java.time.Duration
import kotlin.test.Test

class AutoClosableCleanerTest {

	@Test
	fun testWeakReference() {
		var a: Any? = Object()
		val w = WeakReference(a)
		a = null
		System.gc()
		assertNull(w.get())
	}

	@Test
	fun test1() {
		var count = 0
		val c = AutoCloseable {
			count++
		}
		var a: Any? = Object()
		AutoClosableCleaner.register(a!!, c)
		a = null
		assertTimeoutPreemptively(Duration.ofSeconds(5)) {
			while(count == 0) {
				System.gc()
			}
		}
		assertEquals(count, 1)
	}
}
