package com.greentree.commons.tests.aop

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MathKtTest {

	@Test
	fun gcd_2_4_to_2() {
		assertEquals(gcd(2, 4), 2)
	}
}