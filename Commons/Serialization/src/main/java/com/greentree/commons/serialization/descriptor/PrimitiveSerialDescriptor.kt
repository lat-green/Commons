package com.greentree.commons.serialization.descriptor

import com.greentree.commons.serialization.CompositeDecoder
import com.greentree.commons.serialization.CompositeEncoder
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

	override fun getElementDescriptor(index: Int): SerialDescriptor<*> {
		TODO("Not yet implemented")
	}

	override fun isElementOptional(index: Int) = false
}

object IntSerialDescriptor : PrimitiveSerialDescriptor<Int>(Int::class.java) {

	override fun encode(encoder: CompositeEncoder, value: Int) = encoder.encodeIntElement(value)
	override fun decode(decoder: CompositeDecoder) = decoder.decodeIntElement()
}

object StringSerialDescriptor : PrimitiveSerialDescriptor<String>(String::class.java) {

	override fun encode(encoder: CompositeEncoder, value: String) = encoder.encodeStringElement(value)
	override fun decode(decoder: CompositeDecoder) = decoder.decodeStringElement()
}

object ShortSerialDescriptor : PrimitiveSerialDescriptor<Short>(Short::class.java) {

	override fun encode(encoder: CompositeEncoder, value: Short) = encoder.encodeShortElement(value)
	override fun decode(decoder: CompositeDecoder) = decoder.decodeShortElement()
}

data class EnumSerialDescriptor<E : Enum<E>>(private val cls: Class<E>) : PrimitiveSerialDescriptor<E>(cls) {

	override fun encode(encoder: CompositeEncoder, value: E) = encoder.encodeEnumElement(this, value)
	override fun decode(decoder: CompositeDecoder) = decoder.decodeEnumElement(this)
}

data class ObjectSerialDescriptor<T : Any>(private val cls: Class<T>) : PrimitiveSerialDescriptor<T>(cls) {

	override fun encode(encoder: CompositeEncoder, value: T) = encoder.encodeSerializableElement(serializer(cls), value)
	override fun decode(decoder: CompositeDecoder) = decoder.decodeSerializableElement(serializer(cls))
}

val <T : Any> Class<T>.descriptor
	get() = when(this) {
		String::class.java -> StringSerialDescriptor as SerialDescriptor<T>
		Int::class.java -> IntSerialDescriptor as SerialDescriptor<T>
		Short::class.java -> ShortSerialDescriptor as SerialDescriptor<T>
		else -> ReflectionSerialDescriptor(this)
	}