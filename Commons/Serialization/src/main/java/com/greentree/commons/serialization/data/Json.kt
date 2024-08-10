package com.greentree.commons.serialization.data

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonNull
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import com.greentree.commons.serialization.descriptor.SerialDescriptor
import com.greentree.commons.serialization.serializer.DeserializationStrategy
import com.greentree.commons.serialization.serializer.SerializationStrategy
import com.greentree.commons.serialization.serializer.serializer
import kotlin.reflect.KClass

object Json : DecodeDataFormat<JsonElement> {

	override fun decoder(json: JsonElement) = JsonDecoder(json)
	fun encoder(onResult: (JsonElement) -> Unit) = JsonEncoder(onResult)

	inline fun <reified T : Any> encodeToString(value: T) =
		encodeToString(serializer<T>(), value)

	inline fun <reified T : Any> decodeFromString(value: JsonElement) =
		decodeFromString(serializer<T>(), value)

	fun <T : Any> encodeToString(serializer: SerializationStrategy<T>, value: T): JsonElement {
		lateinit var result: JsonElement
		val encoder = encoder {
			result = it
		}
		serializer.serialize(encoder, value)
		return result
	}

	fun <T : Any> decodeFromString(serializer: DeserializationStrategy<T>, value: JsonElement): T {
		val decoder = decoder(value)
		return serializer.deserialize(decoder)
	}

	fun <T : Any> encodeToString(cls: KClass<in T>, value: T) = encodeToString(serializer(cls), value)

	fun <T : Any> decodeFromString(cls: KClass<T>, value: JsonElement) =
		decodeFromString(serializer(cls), value)

	fun <T : Any> encodeToString(cls: Class<in T>, value: T) = encodeToString(serializer(cls), value)

	fun <T : Any> decodeFromString(cls: Class<T>, value: JsonElement) =
		decodeFromString(serializer(cls), value)
}

class JsonEncoder(val onResult: (JsonElement) -> Unit) : Encoder {

	private var isDone = false

	private fun setResult(result: JsonElement) {
		require(!isDone)
		isDone = true
		onResult(result)
	}

	override fun encodeBoolean(value: Boolean) {
		setResult(JsonPrimitive(value))
	}

	override fun encodeByte(value: Byte) {
		setResult(JsonPrimitive(value))
	}

	override fun encodeChar(value: Char) {
		setResult(JsonPrimitive(value))
	}

	override fun encodeShort(value: Short) {
		setResult(JsonPrimitive(value))
	}

	override fun encodeInt(value: Int) {
		setResult(JsonPrimitive(value))
	}

	override fun encodeLong(value: Long) {
		setResult(JsonPrimitive(value))
	}

	override fun encodeFloat(value: Float) {
		setResult(JsonPrimitive(value))
	}

	override fun encodeDouble(value: Double) {
		setResult(JsonPrimitive(value))
	}

	override fun encodeString(value: String) {
		setResult(JsonPrimitive(value))
	}

	override fun beginStructure(descriptor: SerialDescriptor): Structure<Encoder> {
		val result = JsonObject()

		return object : Structure<Encoder> {
			override fun field(name: String): Encoder {
				val res = JsonEncoder {
					result.add(name, it)
				}
				return res
			}

			override fun field(index: Int) = field(descriptor.getElementName(index))

			override fun close() {
				setResult(result)
			}
		}
	}

	override fun beginCollection(descriptor: SerialDescriptor): Structure<Encoder> {
		val result = JsonArray()

		return object : Structure<Encoder> {
			override fun field(name: String) = field(name.toInt())

			override fun field(index: Int): JsonEncoder {
				val res = JsonEncoder {
					result[index] = it
				}
				return res
			}

			override fun close() {
				setResult(result)
			}
		}
	}
}

class JsonDecoder(private val element: JsonElement) : Decoder {

	override fun decodeBoolean(): Boolean = element.asBoolean

	override fun decodeByte(): Byte = element.asByte

	override fun decodeChar(): Char = element.asCharacter

	override fun decodeShort(): Short = element.asShort

	override fun decodeInt(): Int = element.asInt

	override fun decodeLong(): Long = element.asLong

	override fun decodeFloat(): Float = element.asFloat

	override fun decodeDouble(): Double = element.asDouble

	override fun decodeString(): String = element.asString

	override fun beginCollection(descriptor: SerialDescriptor): Structure<Decoder> {
		val element = element.asJsonArray

		return object : Structure<Decoder> {
			override fun field(name: String) = field(name.toInt())

			override fun field(index: Int) = JsonDecoder(element.get(index))
		}
	}

	override fun beginStructure(descriptor: SerialDescriptor): Structure<Decoder> {
		val element = element.asJsonObject

		return object : Structure<Decoder> {
			override fun field(name: String) = JsonDecoder(
				if(element.has(name))
					element.get(name)
				else
					JsonNull.INSTANCE
			)

			override fun field(index: Int) = field(descriptor.getElementName(index))
		}
	}
}