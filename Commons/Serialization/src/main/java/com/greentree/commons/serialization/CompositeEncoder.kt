package com.greentree.commons.serialization

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
}
