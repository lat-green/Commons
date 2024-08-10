package test.com.greentree.commons.serialization

import com.greentree.commons.serialization.data.Bytes
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ArgumentsSource
import test.data.CustomPerson
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import kotlin.reflect.KClass

class BytesTest {

	@ParameterizedTest
	@ArgumentsSource(SerializationArgumentsProvider::class)
	fun <T : Any> withRealClass(value: T) {
		val cls = value::class as KClass<T>
		val arr = ByteArrayOutputStream().use { stream ->
			Bytes.encodeToSteam(cls, value, stream)
			stream.toByteArray()
		}
		val decodeValue = ByteArrayInputStream(arr).use { stream ->
			Bytes.decodeFromSteam(cls, stream)
		}
		assertEquals(value, decodeValue)
	}

	@ParameterizedTest
	@ArgumentsSource(SerializationArgumentsProvider::class)
	fun <T : Any> withAnyClass(value: T) {
		val cls = Any::class
		val arr = ByteArrayOutputStream().use { stream ->
			Bytes.encodeToSteam(cls, value, stream)
			stream.toByteArray()
		}
		val decodeValue = ByteArrayInputStream(arr).use { stream ->
			Bytes.decodeFromSteam(cls, stream)
		}
		assertEquals(value, decodeValue)
	}

	@Disabled
	@Test
	fun reflectCollection() {
		val list = mutableListOf<Any>()
		list.add(list)
		val arr = ByteArrayOutputStream().use { stream ->
			Bytes.encodeToSteam(list, stream)
			stream.toByteArray()
		}
		val deserializeList = ByteArrayInputStream(arr).use { stream ->
			Bytes.decodeFromSteam<CustomPerson>(stream)
		}
		assertEquals(list, deserializeList)
	}
}
