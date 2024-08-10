package test.com.greentree.commons.serialization

import com.greentree.commons.serialization.data.Json
import com.greentree.commons.serialization.serializer.serializer
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ArgumentsSource
import kotlin.reflect.KClass

class JsonTest {

	@ParameterizedTest
	@ArgumentsSource(SerializationArgumentsProvider::class)
	fun <T : Any> withRealClass(value: T) {
		val cls = value::class as KClass<T>
		val json = Json.encodeToString(cls, value)
		val decodeValue = Json.decodeFromString(cls, json)
		assertEquals(value, decodeValue)
	}

	@ParameterizedTest
	@ArgumentsSource(SerializationArgumentsProvider::class)
	fun <T : Any> withAnyClass(value: T) {
		val cls = Any::class
		val json = Json.encodeToString(cls, value)
		val decodeValue = Json.decodeFromString(cls, json)
		assertEquals(value, decodeValue)
	}

	@Disabled
	@Test
	fun reflectCollection() {
		val list = mutableListOf<Any>()
		list.add(list)
		val json = Json.encodeToString(list)
		val deserializeList = serializer(list::class).deserialize(Json.decoder(json))
		assertEquals(list, deserializeList)
	}
}