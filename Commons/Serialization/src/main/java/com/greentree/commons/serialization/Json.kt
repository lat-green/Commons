package com.greentree.commons.serialization

import com.greentree.commons.serialization.descriptor.SerialDescriptor
import com.greentree.commons.serialization.serializer.DeserializationStrategy
import com.greentree.commons.serialization.serializer.SerializationStrategy
import com.greentree.commons.serialization.serializer.serializer

object Json {

	fun decoder(json: String) = JsonDecoder(json)
	fun encoder() = JsonEncoder()

	inline fun <reified T : Any> encodeToString(value: T): String {
		val encoder = encoder()
		serializer<T>().serialize(encoder, value)
		return encoder.result
	}

	inline fun <reified T : Any> decodeFromString(value: String): T {
		val decoder = decoder(value)
		return serializer<T>().deserialize(decoder)
	}
}

class JsonEncoder : Encoder {

	val result
		get() = resultSupplier()
	private var resultSupplier = {
		""
	}

	override fun encodeBoolean(value: Boolean) {
		TODO("Not yet implemented")
	}

	override fun encodeByte(value: Byte) {
		TODO("Not yet implemented")
	}

	override fun encodeChar(value: Char) {
		TODO("Not yet implemented")
	}

	override fun encodeShort(value: Short) {
		TODO("Not yet implemented")
	}

	override fun encodeInt(value: Int) {
		TODO("Not yet implemented")
	}

	override fun encodeLong(value: Long) {
		TODO("Not yet implemented")
	}

	override fun encodeFloat(value: Float) {
		TODO("Not yet implemented")
	}

	override fun encodeDouble(value: Double) {
		TODO("Not yet implemented")
	}

	override fun encodeString(value: String) {
		TODO("Not yet implemented")
	}

	override fun <T> encodeSerializable(serializer: SerializationStrategy<T>, value: T) {
		TODO("Not yet implemented")
	}

	override fun beginStructure(descriptor: SerialDescriptor<*>) = JsonObjectEncoder(descriptor).also {
		addResult {
			it.result
		}
	}

	override fun <E : Enum<E>> encodeEnumElement(descriptor: SerialDescriptor<E>, value: E) {
		TODO("Not yet implemented")
	}

	private fun addResult(text: () -> String) {
		val result = resultSupplier
		resultSupplier = {
			val a = result()
			val b = text()
			if(a.isBlank())
				b
			else
				"$a,$b"
		}
	}
}

class JsonObjectEncoder(private val descriptor: SerialDescriptor<*>) : CompositeEncoder {

	val result
		get() = "{${resultSupplier()}}"
	private var resultSupplier = {
		""
	}
	private var _index = 0
	val index
		get() = _index++

	override fun encodeBooleanElement(value: Boolean) {
		val name = descriptor.getElementName(index)
		addResult("\"$name\":$value")
	}

	override fun encodeByteElement(value: Byte) {
		TODO("Not yet implemented")
	}

	override fun encodeShortElement(value: Short) {
		TODO("Not yet implemented")
	}

	private fun addResult(text: String) {
		val result = resultSupplier
		resultSupplier = {
			val a = result()
			if(a.isBlank())
				text
			else
				"$a,$text"
		}
	}

	private fun addResult(text: () -> String) {
		val result = resultSupplier
		resultSupplier = {
			val a = result()
			val b = text()
			if(a.isBlank())
				b
			else
				"$a,$b"
		}
	}

	override fun encodeIntElement(value: Int) {
		val name = descriptor.getElementName(index)
		addResult("\"$name\":$value")
	}

	override fun encodeLongElement(value: Long) {
		TODO("Not yet implemented")
	}

	override fun encodeStringElement(value: String) {
		val name = descriptor.getElementName(index)
		addResult("\"$name\":\"$value\"")
	}

	override fun encodeFloatElement(value: Float) {
		TODO("Not yet implemented")
	}

	override fun encodeDoubleElement(value: Double) {
		TODO("Not yet implemented")
	}

	override fun <T> encodeSerializableElement(serializer: SerializationStrategy<T>, value: T) {
		val name = descriptor.getElementName(index)
		val encoder = Json.encoder()
		serializer.serialize(encoder, value)
		addResult {
			"\"$name\":${encoder.result}"
		}
	}

	override fun <E : Enum<E>> encodeEnumElement(descriptor: SerialDescriptor<E>, value: E) {
		TODO("Not yet implemented")
	}
}

data class JsonDecoder(val json: String) : Decoder {

	override fun decodeBoolean(): Boolean {
		TODO("Not yet implemented")
	}

	override fun decodeByte(): Byte {
		TODO("Not yet implemented")
	}

	override fun decodeChar(): Char {
		TODO("Not yet implemented")
	}

	override fun decodeShort(): Short {
		TODO("Not yet implemented")
	}

	override fun decodeInt(): Int {
		TODO("Not yet implemented")
	}

	override fun decodeLong(): Long {
		TODO("Not yet implemented")
	}

	override fun decodeFloat(): Float {
		TODO("Not yet implemented")
	}

	override fun decodeDouble(): Double {
		TODO("Not yet implemented")
	}

	override fun decodeString(): String {
		TODO("Not yet implemented")
	}

	override fun beginStructure(descriptor: SerialDescriptor<*>) = JsonObjectDecoder(json, descriptor)

	override fun <T> decodeSerializableValue(deserializer: DeserializationStrategy<T>): T {
		TODO("Not yet implemented")
	}

	override fun <E : Enum<E>> decodeEnum(enumDescriptor: SerialDescriptor<E>): E {
		TODO("Not yet implemented")
	}
}

class JsonObjectDecoder(val json: String, val descriptor: SerialDescriptor<*>) : CompositeDecoder {

	private var _index = 0
	val index
		get() = _index++

	override fun decodeBooleanElement(): Boolean {
		TODO("Not yet implemented")
	}

	override fun decodeByteElement(): Byte {
		TODO("Not yet implemented")
	}

	override fun decodeCharElement(): Char {
		TODO("Not yet implemented")
	}

	override fun decodeShortElement(): Short {
		TODO("Not yet implemented")
	}

	override fun decodeIntElement(): Int {
		val begin = getIndexByName(descriptor.getElementName(index))
		val end = json.indexOfAny(charArrayOf(',', '}'), begin)
		return json.substring(begin, end).strip().toInt()
	}

	override fun decodeLongElement(): Long {
		TODO("Not yet implemented")
	}

	override fun decodeDoubleElement(): Double {
		TODO("Not yet implemented")
	}

	override fun decodeStringElement(): String {
		val begin = getIndexByName(descriptor.getElementName(index)) + 1
		val end = json.indexOf('"', begin)
		return json.substring(begin, end)
	}

	override fun <T> decodeSerializableElement(deserializer: DeserializationStrategy<T>): T {
		TODO("Not yet implemented")
	}

	override fun <E : Enum<E>> decodeEnumElement(descriptor: SerialDescriptor<E>): E {
		TODO("Not yet implemented")
	}

	private fun getIndexByName(name: String): Int {
		val name = "\"$name\""
		var index = json.indexOf(name)
		if(index == -1)
			throw IllegalArgumentException("name = $name not found in $json")
		index += name.length + 1
		return index
	}
}
