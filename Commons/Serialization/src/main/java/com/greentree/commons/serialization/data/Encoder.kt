package com.greentree.commons.serialization.data

import com.greentree.commons.serialization.descriptor.SerialDescriptor
import com.greentree.commons.serialization.serializer.serializer
import kotlin.reflect.KClass

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

	fun beginStructure(descriptor: SerialDescriptor): Structure<Encoder>
	fun beginCollection(descriptor: SerialDescriptor): Structure<Encoder>
}

fun <T : Any> Encoder.encodeSerializable(baseClass: Class<T>, value: T) {
	serializer(baseClass).serialize(this, value)
}

fun <T : Any> Encoder.encodeSerializable(baseClass: KClass<T>, value: T) {
	serializer(baseClass).serialize(this, value)
}

fun <T : Any> Encoder.encodeSerializable(value: T) = encodeSerializable(value.javaClass, value)
