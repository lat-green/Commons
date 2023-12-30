package com.greentree.commons.serialization.data

import com.greentree.commons.serialization.descriptor.SerialDescriptor
import com.greentree.commons.serialization.serializer.serializer
import kotlin.reflect.KClass

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

	fun beginStructure(descriptor: SerialDescriptor<*>): Structure<Decoder>
}

fun <T : Any> Decoder.decodeSerializable(cls: KClass<T>) = decodeSerializable(cls.java)
fun <T : Any> Decoder.decodeSerializable(cls: Class<T>) = serializer(cls).deserialize(this)

inline fun <reified T : Any> Decoder.decodeSerializable() = decodeSerializable(T::class.java)
