package com.greentree.commons.serialization.data

import com.greentree.commons.serialization.descriptor.SerialDescriptor
import com.greentree.commons.serialization.serializer.DeserializationStrategy
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

	fun beginStructure(descriptor: SerialDescriptor): Structure<Decoder>
	fun beginCollection(descriptor: SerialDescriptor): Structure<Decoder>

	fun <T> decodeSerializable(deserializator: DeserializationStrategy<T>): T = deserializator.deserialize(this)
}

fun <T : Any> Decoder.decodeSerializable(cls: KClass<T>) = decodeSerializable(serializer(cls))
fun <T : Any> Decoder.decodeSerializable(cls: Class<T>) = decodeSerializable(serializer(cls))

inline fun <reified T : Any> Decoder.decodeSerializable() = decodeSerializable(serializer<T>())
