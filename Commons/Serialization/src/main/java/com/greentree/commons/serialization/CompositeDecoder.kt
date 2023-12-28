package com.greentree.commons.serialization

import com.greentree.commons.serialization.descriptor.SerialDescriptor
import com.greentree.commons.serialization.serializer.DeserializationStrategy

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


	fun <E : Enum<E>> decodeEnumElement(descriptor: SerialDescriptor<E>): E
}
