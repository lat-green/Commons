package test.com.greentree.commons.serialization.accuracy

import com.greentree.commons.serialization.serializator.accuracy.FloatAccuracy
import com.greentree.commons.serialization.serializator.accuracy.IntAccuracy
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class AccuracyCalculatorTest {

	@Test
	fun boundsIntAccuracy() {
		val accuracy = IntAccuracy.Calculator(-100, 100, 1)
		assertEquals(0L, accuracy.encodeValue(-100))
		assertEquals(200L, accuracy.encodeValue(100))
		assertEquals(-100, accuracy.decodeValue(0L))
		assertEquals(100, accuracy.decodeValue(200L))
	}

	@Test
	fun boundsFloatAccuracy() {
		val accuracy = FloatAccuracy.Calculator(-100f, 100f, 1f)
		assertEquals(0L, accuracy.encodeValue(-100f))
		assertEquals(200L, accuracy.encodeValue(100f))
		assertEquals(-100f, accuracy.decodeValue(0L))
		assertEquals(100f, accuracy.decodeValue(200L))
	}
}