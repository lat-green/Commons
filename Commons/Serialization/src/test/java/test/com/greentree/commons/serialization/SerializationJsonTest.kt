package test.com.greentree.commons.serialization

import com.greentree.commons.reflection.info.ParameterizedTypeInfo
import com.greentree.commons.serialization.format.Json
import com.greentree.commons.serialization.serializator.deserialize
import com.greentree.commons.serialization.serializator.manager.serializator
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ArgumentsSource

class SerializationJsonTest : SerializationTest() {

	@ArgumentsSource(BaseArgumentsProvider::class)
	@ParameterizedTest
	fun <T : Any> serialize(expected: T, maxSize: Int) {
		val serializator = manager.serializator(expected::class.java)
		val json = Json.encodeToString(serializator, expected)
		val decoder = Json.decoder(json)
		val actual = serializator.deserialize(decoder)
		assertEquals(expected, actual) { "$expected $json" }
	}

	@Test
	fun testMap() {
		val expected = mapOf("a" to 1, "b" to 2)
		val serializator = manager.serializator(
			ParameterizedTypeInfo.fromClass<Map<String, Int>>(
				String::class,
				Int::class
			)
		)
		println(
			ParameterizedTypeInfo.fromClass<Map<String, Int>>(
				String::class,
				Int::class
			)
		)
		val json = Json.encodeToString(serializator, expected)
		println(json.toPrettyString())
		val decoder = Json.decoder(json)
		val actual = serializator.deserialize(decoder)
		assertEquals(expected, actual) { "$expected $json" }
	}
}