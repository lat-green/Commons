package com.greentree.commons.serialization

interface Decoder {

	fun decodeBoolean(): Boolean

	fun decodeByte(): Byte
	fun decodeChar(): Char
	fun decodeShort(): Short
	fun decodeInt(): Int
	fun decodeLong(): Long

	fun decodeFloat(): Float
	fun decodeDouble(): Double

	fun decodeString(): String

	fun beginStructure(descriptor: SerialDescriptor): CompositeDecoder
	fun <T> decodeSerializableValue(deserializer: DeserializationStrategy<T>): T

	fun decodeEnum(enumDescriptor: SerialDescriptor): Int
}
