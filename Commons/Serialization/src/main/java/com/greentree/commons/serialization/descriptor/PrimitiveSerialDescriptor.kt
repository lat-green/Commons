package com.greentree.commons.serialization.descriptor

import kotlin.reflect.KClass

abstract class PrimitiveSerialDescriptor(cls: Class<*>) : SerialDescriptor {

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

	override fun getElementDescriptor(index: Int): SerialDescriptor {
		TODO("Not yet implemented")
	}
}

object ByteSerialDescriptor : PrimitiveSerialDescriptor(Byte::class.java)

object CharSerialDescriptor : PrimitiveSerialDescriptor(Char::class.java)

object ShortSerialDescriptor : PrimitiveSerialDescriptor(Short::class.java)

object IntSerialDescriptor : PrimitiveSerialDescriptor(Int::class.java)

object LongSerialDescriptor : PrimitiveSerialDescriptor(Long::class.java)

object FloatSerialDescriptor : PrimitiveSerialDescriptor(Float::class.java)

object BooleanSerialDescriptor : PrimitiveSerialDescriptor(Boolean::class.java)

object DoubleSerialDescriptor : PrimitiveSerialDescriptor(Double::class.java)

object StringSerialDescriptor : PrimitiveSerialDescriptor(String::class.java)

data class EnumSerialDescriptor(private val cls: Class<*>) : PrimitiveSerialDescriptor(cls) {

	init {
		require(cls.isEnum)
	}
}

data class KotlinObjectSerialDescriptor<T : Any>(
	private val cls: KClass<T>
) : PrimitiveSerialDescriptor(cls.java)

val <T : Any> Class<T>.descriptor
	get() = when {
		isPrimitive -> when(this) {
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

		isEnum -> EnumSerialDescriptor(this as Class<out Enum<*>>)
		else -> ReflectionSerialDescriptor(this)
	}