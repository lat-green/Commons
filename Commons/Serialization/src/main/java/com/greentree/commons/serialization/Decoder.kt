package com.greentree.commons.serialization

import com.greentree.commons.serialization.descriptor.SerialDescriptor
import com.greentree.commons.serialization.serializer.DeserializationStrategy

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

	fun beginStructure(descriptor: SerialDescriptor<*>): CompositeDecoder
	fun <T> decodeSerializableValue(deserializer: DeserializationStrategy<T>): T

	fun <E : Enum<E>> decodeEnum(enumDescriptor: SerialDescriptor<E>): E
}
