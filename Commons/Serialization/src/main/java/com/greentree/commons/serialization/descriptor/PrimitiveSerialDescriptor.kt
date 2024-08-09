package com.greentree.commons.serialization.descriptor

import com.greentree.commons.serialization.data.Decoder
import com.greentree.commons.serialization.data.Encoder
import com.greentree.commons.serialization.serializer.isKotlin
import kotlin.reflect.KClass

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

object ByteSerialDescriptor : PrimitiveSerialDescriptor<Byte>(Byte::class.java) {

	override fun encode(encoder: Encoder, value: Byte) = encoder.encodeByte(value)
	override fun decode(decoder: Decoder) = decoder.decodeByte()
}

object ShortSerialDescriptor : PrimitiveSerialDescriptor<Short>(Short::class.java) {

	override fun encode(encoder: Encoder, value: Short) = encoder.encodeShort(value)
	override fun decode(decoder: Decoder) = decoder.decodeShort()
}

object IntSerialDescriptor : PrimitiveSerialDescriptor<Int>(Int::class.java) {

	override fun encode(encoder: Encoder, value: Int) = encoder.encodeInt(value)
	override fun decode(decoder: Decoder) = decoder.decodeInt()
}

object LongSerialDescriptor : PrimitiveSerialDescriptor<Long>(Long::class.java) {

	override fun encode(encoder: Encoder, value: Long) = encoder.encodeLong(value)
	override fun decode(decoder: Decoder) = decoder.decodeLong()
}

object FloatSerialDescriptor : PrimitiveSerialDescriptor<Float>(Float::class.java) {

	override fun encode(encoder: Encoder, value: Float) = encoder.encodeFloat(value)
	override fun decode(decoder: Decoder) = decoder.decodeFloat()
}

object BooleanSerialDescriptor : PrimitiveSerialDescriptor<Boolean>(Boolean::class.java) {

	override fun encode(encoder: Encoder, value: Boolean) = encoder.encodeBoolean(value)
	override fun decode(decoder: Decoder) = decoder.decodeBoolean()
}

object DoubleSerialDescriptor : PrimitiveSerialDescriptor<Double>(Double::class.java) {

	override fun encode(encoder: Encoder, value: Double) = encoder.encodeDouble(value)
	override fun decode(decoder: Decoder) = decoder.decodeDouble()
}

object StringSerialDescriptor : PrimitiveSerialDescriptor<String>(String::class.java) {

	override fun encode(encoder: Encoder, value: String) = encoder.encodeString(value)
	override fun decode(decoder: Decoder) = decoder.decodeString()
}

data class EnumSerialDescriptor<E : Enum<E>>(private val cls: Class<E>) : PrimitiveSerialDescriptor<E>(cls) {

	override fun encode(encoder: Encoder, value: E) = encoder.encodeInt(value.ordinal)
	override fun decode(decoder: Decoder) = cls.enumConstants[decoder.decodeInt()]
}

data class ObjectSerialDescriptor<T : Any>(private val cls: KClass<T>) : PrimitiveSerialDescriptor<T>(cls.java) {

	override fun encode(encoder: Encoder, value: T) {}
	override fun decode(decoder: Decoder) = cls.objectInstance!!
}

val <T : Any> Class<T>.descriptor
	get() = when(this) {
		String::class.java -> StringSerialDescriptor as SerialDescriptor<T>
		Byte::class.java -> ByteSerialDescriptor as SerialDescriptor<T>
		Short::class.java -> ShortSerialDescriptor as SerialDescriptor<T>
		Int::class.java -> IntSerialDescriptor as SerialDescriptor<T>
		Integer::class.java -> IntSerialDescriptor as SerialDescriptor<T>
		Long::class.java -> LongSerialDescriptor
		Float::class.java -> FloatSerialDescriptor as SerialDescriptor<T>
		Double::class.java -> DoubleSerialDescriptor
		Boolean::class.java -> BooleanSerialDescriptor
		else -> when {
			isKotlin(kotlin) && kotlin.objectInstance != null -> ObjectSerialDescriptor(kotlin)
			else -> ReflectionSerialDescriptor(this)
		}
	} as SerialDescriptor<T>