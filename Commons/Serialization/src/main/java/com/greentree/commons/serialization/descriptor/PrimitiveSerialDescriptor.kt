package com.greentree.commons.serialization.descriptor

import com.greentree.commons.serialization.data.Decoder
import com.greentree.commons.serialization.data.Encoder

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

val <T : Any> Class<T>.descriptor
	get() = run {
		if(isPrimitive) when(this) {
			String::class.java -> StringSerialDescriptor
			Byte::class.java -> ByteSerialDescriptor
			Short::class.java -> ShortSerialDescriptor
			Int::class.java -> IntSerialDescriptor
			Integer::class.java -> IntSerialDescriptor
			Long::class.java -> LongSerialDescriptor
			Float::class.java -> FloatSerialDescriptor
			Double::class.java -> DoubleSerialDescriptor
			Boolean::class.java -> BooleanSerialDescriptor
			else -> TODO("$this")
		}
		else ReflectionSerialDescriptor(this)
	} as SerialDescriptor<T>