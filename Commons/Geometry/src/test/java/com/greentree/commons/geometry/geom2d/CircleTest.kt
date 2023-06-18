package com.greentree.commons.geometry.geom2d

import com.greentree.commons.math.MathLine1D
import com.greentree.commons.math.vector.Vector2f
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class CircleTest {

	@Test
	fun test1() {
		val n = Vector2f(0f, 1f).normalize()
		var p = Circle.scale(2f, 1f).projection(n)
		Assertions.assertEquals(p, MathLine1D(-2f, 2f))
	}

	@Test
	fun test2() {
		val n = Vector2f(1f, 0f).normalize()
		var p = Circle.scale(2f, 1f).projection(n)
		Assertions.assertEquals(p, MathLine1D(-1f, 1f))
	}
}