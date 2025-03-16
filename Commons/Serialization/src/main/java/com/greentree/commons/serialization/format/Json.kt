package com.greentree.commons.serialization.format

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.greentree.commons.serialization.serializator.DeserializationStrategy
import com.greentree.commons.serialization.serializator.SerializationStrategy
import com.greentree.commons.serialization.serializator.deserialize
import com.greentree.commons.serialization.serializator.manager.SerializatorManager
import com.greentree.commons.serialization.serializator.manager.serializator
import com.greentree.commons.serialization.serializator.serialize
import kotlin.reflect.KClass

object Json {

	fun decoder(json: JsonNode): Decoder = JsonDecoder(json)
	fun encoder(onResult: (JsonNode) -> Unit): Encoder = JsonEncoder(onResult)

	inline fun <reified T : Any> SerializatorManager.encodeToString(value: T) =
		encodeToString(serializator<T>(), value)

	inline fun <reified T : Any> SerializatorManager.decodeFromString(value: JsonNode) =
		decodeFromString(serializator<T>(), value)

	fun <T : Any> encodeToString(serializer: SerializationStrategy<T>, value: T): JsonNode {
		lateinit var result: JsonNode
		encoder {
			result = it
		}.use { encoder ->
			serializer.serialize(encoder, value)
		}
		return result
	}

	fun <T : Any> decodeFromString(serializer: DeserializationStrategy<T>, value: JsonNode): T {
		return decoder(value).use { decoder ->
			serializer.deserialize(decoder)
		}
	}

	fun <T : Any> SerializatorManager.encodeToString(cls: KClass<in T>, value: T) =
		encodeToString(serializator(cls), value)

	fun <T : Any> SerializatorManager.decodeFromString(cls: KClass<T>, value: JsonNode) =
		decodeFromString(serializator(cls), value)

	fun <T : Any> SerializatorManager.encodeToString(cls: Class<in T>, value: T) =
		encodeToString(serializator(cls), value)

	fun <T : Any> SerializatorManager.decodeFromString(cls: Class<T>, value: JsonNode) =
		decodeFromString(serializator(cls), value)
}

private val objectMapper = ObjectMapper()
private val nodeFactory = objectMapper.nodeFactory

data class JsonEncoder(val onResult: (JsonNode) -> Unit) : Encoder {

	private lateinit var result: JsonNode

	private fun setResult(result: JsonNode) {
		require(!::result.isInitialized) { "already set result current: ${this.result} new: $result" }
		this.result = result
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

	override fun beginStructure(): Structure<Encoder> {
		val result = nodeFactory.objectNode()

		return object : Structure<Encoder> {
			override fun field(name: String): Encoder {
				val res = JsonEncoder {
					result.put(name, it)
				}
				return res
			}

			override fun field(index: Int): Encoder = field(index.toString())

			override fun close() {
				setResult(result)
			}
		}
	}

	override fun beginCollection(): Structure<Encoder> {
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

	override fun close() {
		if(::result.isInitialized)
			onResult(result)
		else
			onResult(nodeFactory.objectNode())
	}
}

data class JsonDecoder(private val element: JsonNode) : Decoder {

	override fun decodeBoolean(): Boolean = element.booleanValue()

	override fun decodeByte(): Byte = element.numberValue().toByte()

	override fun decodeChar(): Char = element.textValue().get(0)

	override fun decodeShort(): Short = element.numberValue().toShort()

	override fun decodeInt(): Int = element.intValue()

	override fun decodeLong(): Long = element.longValue()

	override fun decodeFloat(): Float = element.floatValue()

	override fun decodeDouble(): Double = element.doubleValue()

	override fun decodeString(): String = element.textValue()

	override fun beginCollection(): Structure<Decoder> {
		val element = element

		return object : Structure<Decoder> {
			override fun field(name: String) = field(name.toInt())

			override fun field(index: Int) = JsonDecoder(element.get(index))
		}
	}

	override fun close() {
	}

	override fun beginStructure(): Structure<Decoder> {
		val element = element

		return object : Structure<Decoder> {
			override fun field(name: String) = JsonDecoder(
				if(element.has(name))
					element.get(name)
				else
					nodeFactory.nullNode()
			)

			override fun field(index: Int): Decoder = JsonDecoder(
				if(element.has(index))
					element.get(index)
				else
					nodeFactory.nullNode()
			)
//			override fun field(index: Int) = field(descriptor.getElementName(index))
		}
	}
}