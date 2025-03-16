package test.com.greentree.commons.serialization

import com.greentree.commons.serialization.format.Json
import com.greentree.commons.serialization.serializator.deserialize
import org.junit.jupiter.api.Assertions.*
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
}