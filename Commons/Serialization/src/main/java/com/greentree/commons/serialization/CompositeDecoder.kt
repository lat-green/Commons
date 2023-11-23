package com.greentree.commons.serialization

interface CompositeDecoder {

	fun decodeBooleanElement(): Boolean

	fun decodeByteElement(): Byte
	fun decodeCharElement(): Char
	fun decodeShortElement(): Short
	fun decodeIntElement(): Int
	fun decodeLongElement(): Long
	fun decodeDoubleElement(): Double

	fun decodeStringElement(): String

	fun <T> decodeSerializableElement(
		deserializer: DeserializationStrategy<T>,
	): T
}
