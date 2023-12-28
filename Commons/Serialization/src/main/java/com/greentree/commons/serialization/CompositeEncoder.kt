package com.greentree.commons.serialization

import com.greentree.commons.serialization.descriptor.SerialDescriptor
import com.greentree.commons.serialization.serializer.SerializationStrategy

interface CompositeEncoder {

	fun encodeBooleanElement(value: Boolean)
	fun encodeByteElement(value: Byte)
	fun encodeShortElement(value: Short)
	fun encodeIntElement(value: Int)
	fun encodeLongElement(value: Long)

	fun encodeStringElement(value: String)

	fun encodeFloatElement(value: Float)
	fun encodeDoubleElement(value: Double)

	fun <T> encodeSerializableElement(
		serializer: SerializationStrategy<T>,
		value: T,
	)
	
	fun <E : Enum<E>> encodeEnumElement(descriptor: SerialDescriptor<E>, value: E)
}
