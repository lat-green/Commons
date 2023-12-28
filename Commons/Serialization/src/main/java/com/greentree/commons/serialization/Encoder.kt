package com.greentree.commons.serialization

import com.greentree.commons.serialization.descriptor.SerialDescriptor
import com.greentree.commons.serialization.serializer.SerializationStrategy

interface Encoder {

	fun encodeBoolean(value: Boolean)

	fun encodeByte(value: Byte)
	fun encodeChar(value: Char)
	fun encodeShort(value: Short)
	fun encodeInt(value: Int)
	fun encodeLong(value: Long)

	fun encodeFloat(value: Float)
	fun encodeDouble(value: Double)

	fun encodeString(value: String)

	fun <T> encodeSerializable(
		serializer: SerializationStrategy<T>,
		value: T,
	)

	fun beginStructure(descriptor: SerialDescriptor<*>): CompositeEncoder

	fun <E : Enum<E>> encodeEnumElement(descriptor: SerialDescriptor<E>, value: E)
}
