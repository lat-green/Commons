package com.greentree.commons.signals

import kotlin.test.Test
import kotlin.test.assertEquals

class ComputedSignalTest {

	@Test
	fun getValue() {
		val s1 by signal("A")
		val s2 by signal("B")
		val s by computed {
			s1 + s2
		}
		assertEquals(s, "AB")
	}

	@Test
	fun effect() {
		var c = 0
		var s by signal("A")
		effect {
			c++
			consume(s)
		}.use {
			s = "B"
		}
		s = "C"
		assertEquals(s, "C")
		assertEquals(c, 2)
	}

	@Test
	fun basic() {
		var c = 0
		var s1 by signal("A")
		var s2 by signal("B")
		val s by computed {
			c++
			s1 + s2
		}
		assertEquals(s, "AB")
		s1 = "C"
		s2 = "C"
		assertEquals(s, "CC")
		assertEquals(c, 2)
	}

	@Test
	fun batch() {
		var c = 0
		var s1 by signal("A")
		var s2 by signal("B")
		val s by computed {
			c++
			s1 + s2
		}
		assertEquals(s, "AB")
		batch {
			s1 = "C"
			s2 = "C"
		}
		assertEquals(s, "CC")
		assertEquals(c, 2)
	}
}

fun consume(value: Any) {
}