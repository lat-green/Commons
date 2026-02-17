package test.com.greentree.commons.serialization

import com.greentree.commons.serialization.format.Bytes
import com.greentree.commons.serialization.serializator.deserialize
import com.greentree.commons.serialization.serializator.serialize
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ArgumentsSource
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class SerializationBytesTest : SerializationTest() {

	@ArgumentsSource(BaseArgumentsProvider::class)
	@ParameterizedTest
	fun <T : Any> serialize(data: TestData<T>) {
		val expected = data.value
		val maxSize = data.maxByteSize
		val serializator = manager.serializator(data.type)
		val array = ByteArrayOutputStream().use {
			Bytes.encoder(it).use { encoder ->
				serializator.serialize(encoder, expected)
			}
			it.toByteArray()
		}
		val actual = ByteArrayInputStream(array).use {
			val decoder = Bytes.decoder(it)
			serializator.deserialize(decoder)
		}
		assertEquals(
			expected,
			actual
		) { "$expected\n${array.contentToString()} ${array.size}\n$maxSize\n${String(array)}" }
		assertTrue(array.size <= maxSize) {
			"memory limit\n$expected\n${array.contentToString()}\n${array.size} <= $maxSize\n${
				String(
					array
				)
			}"
		}
	}
}