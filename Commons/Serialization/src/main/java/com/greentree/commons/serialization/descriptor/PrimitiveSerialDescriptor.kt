package com.greentree.commons.serialization.descriptor

import com.greentree.commons.serialization.Decoder
import com.greentree.commons.serialization.Encoder
import com.greentree.commons.serialization.serializer.serializer

abstract class PrimitiveSerialDescriptor<T>(cls: Class<T>) : SerialDescriptor<T> {

	override val serialName: String = cls.name
	override val elementsCount: Int
		get() = 0

	override fun getElementIndex(name: String): Int {
		TODO("Not yet implemented")
	}

	override fun getElementName(index: Int): String {
		TODO("Not yet implemented")
	}

	override fun isElementOptional(index: Int) = false

	override fun getElementDescriptor(index: Int): SerialDescriptor<*> {
		TODO("Not yet implemented")
	}
}

object IntSerialDescriptor : PrimitiveSerialDescriptor<Int>(Int::class.java) {

	override fun encode(encoder: Encoder, value: Int) = encoder.encodeInt(value)
	override fun decode(decoder: Decoder) = decoder.decodeInt()
}

object StringSerialDescriptor : PrimitiveSerialDescriptor<String>(String::class.java) {

	override fun encode(encoder: Encoder, value: String) = encoder.encodeString(value)
	override fun decode(decoder: Decoder) = decoder.decodeString()
}

object ShortSerialDescriptor : PrimitiveSerialDescriptor<Short>(Short::class.java) {

	override fun encode(encoder: Encoder, value: Short) = encoder.encodeShort(value)
	override fun decode(decoder: Decoder) = decoder.decodeShort()
}

data class EnumSerialDescriptor<E : Enum<E>>(private val cls: Class<E>) : PrimitiveSerialDescriptor<E>(cls) {

	override fun encode(encoder: Encoder, value: E) = encoder.encodeEnum(this, value)
	override fun decode(decoder: Decoder) = decoder.decodeEnum(this)
}

data class ObjectSerialDescriptor<T : Any>(private val cls: Class<T>) : PrimitiveSerialDescriptor<T>(cls) {

	override fun encode(encoder: Encoder, value: T) = serializer(cls).serialize(encoder, value)
	override fun decode(decoder: Decoder) = serializer(cls).deserialize(decoder)
}

val <T : Any> Class<T>.descriptor
	get() = when(this) {
		String::class.java -> StringSerialDescriptor as SerialDescriptor<T>
		Int::class.java -> IntSerialDescriptor as SerialDescriptor<T>
		Short::class.java -> ShortSerialDescriptor as SerialDescriptor<T>
		else -> ReflectionSerialDescriptor(this)
	}