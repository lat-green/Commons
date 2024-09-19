package com.greentree.commons.serialization.data

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.greentree.commons.serialization.descriptor.SerialDescriptor
import com.greentree.commons.serialization.serializer.DeserializationStrategy
import com.greentree.commons.serialization.serializer.SerializationStrategy
import com.greentree.commons.serialization.serializer.serializer
import kotlin.reflect.KClass

object Json : DecodeDataFormat<JsonNode> {

	override fun decoder(json: JsonNode) = JsonDecoder(json)
	fun encoder(onResult: (JsonNode) -> Unit) = JsonEncoder(onResult)

	inline fun <reified T : Any> encodeToString(value: T) =
		encodeToString(serializer<T>(), value)

	inline fun <reified T : Any> decodeFromString(value: JsonNode) =
		decodeFromString(serializer<T>(), value)

	fun <T : Any> encodeToString(serializer: SerializationStrategy<T>, value: T): JsonNode {
		lateinit var result: JsonNode
		val encoder = encoder {
			result = it
		}
		serializer.serialize(encoder, value)
		return result
	}

	fun <T : Any> decodeFromString(serializer: DeserializationStrategy<T>, value: JsonNode): T {
		val decoder = decoder(value)
		return serializer.deserialize(decoder)
	}

	fun <T : Any> encodeToString(cls: KClass<in T>, value: T) = encodeToString(serializer(cls), value)

	fun <T : Any> decodeFromString(cls: KClass<T>, value: JsonNode) =
		decodeFromString(serializer(cls), value)

	fun <T : Any> encodeToString(cls: Class<in T>, value: T) = encodeToString(serializer(cls), value)

	fun <T : Any> decodeFromString(cls: Class<T>, value: JsonNode) =
		decodeFromString(serializer(cls), value)
}

private val objectMapper = ObjectMapper()
private val nodeFactory = objectMapper.nodeFactory

class JsonEncoder(val onResult: (JsonNode) -> Unit) : Encoder {

	private var isDone = false

	private fun setResult(result: JsonNode) {
		require(!isDone)
		isDone = true
		onResult(result)
	}

	override fun encodeBoolean(value: Boolean) {
		setResult(nodeFactory.booleanNode(value))
	}

	override fun encodeByte(value: Byte) {
		setResult(nodeFactory.numberNode(value))
	}

	override fun encodeChar(value: Char) {
		setResult(nodeFactory.textNode(value.toString()))
	}

	override fun encodeShort(value: Short) {
		setResult(nodeFactory.numberNode(value))
	}

	override fun encodeInt(value: Int) {
		setResult(nodeFactory.numberNode(value))
	}

	override fun encodeLong(value: Long) {
		setResult(nodeFactory.numberNode(value))
	}

	override fun encodeFloat(value: Float) {
		setResult(nodeFactory.numberNode(value))
	}

	override fun encodeDouble(value: Double) {
		setResult(nodeFactory.numberNode(value))
	}

	override fun encodeString(value: String) {
		setResult(nodeFactory.textNode(value))
	}

	override fun beginStructure(descriptor: SerialDescriptor): Structure<Encoder> {
		val result = nodeFactory.objectNode()

		return object : Structure<Encoder> {
			override fun field(name: String): Encoder {
				val res = JsonEncoder {
					result.put(name, it)
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
		val result = nodeFactory.arrayNode()

		return object : Structure<Encoder> {
			override fun field(name: String) = field(name.toInt())

			override fun field(index: Int): JsonEncoder {
				val res = JsonEncoder {
					result.insert(index, it)
				}
				return res
			}

			override fun close() {
				setResult(result)
			}
		}
	}
}

class JsonDecoder(private val element: JsonNode) : Decoder {

	override fun decodeBoolean(): Boolean = element.booleanValue()

	override fun decodeByte(): Byte = element.numberValue().toByte()

	override fun decodeChar(): Char = element.textValue().get(0)

	override fun decodeShort(): Short = element.numberValue().toShort()

	override fun decodeInt(): Int = element.intValue()

	override fun decodeLong(): Long = element.longValue()

	override fun decodeFloat(): Float = element.floatValue()

	override fun decodeDouble(): Double = element.doubleValue()

	override fun decodeString(): String = element.textValue()

	override fun beginCollection(descriptor: SerialDescriptor): Structure<Decoder> {
		val element = element

		return object : Structure<Decoder> {
			override fun field(name: String) = field(name.toInt())

			override fun field(index: Int) = JsonDecoder(element.get(index))
		}
	}

	override fun beginStructure(descriptor: SerialDescriptor): Structure<Decoder> {
		val element = element

		return object : Structure<Decoder> {
			override fun field(name: String) = JsonDecoder(
				if(element.has(name))
					element.get(name)
				else
					nodeFactory.nullNode()
			)

			override fun field(index: Int) = field(descriptor.getElementName(index))
		}
	}
}