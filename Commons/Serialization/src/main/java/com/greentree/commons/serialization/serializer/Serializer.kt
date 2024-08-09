package com.greentree.commons.serialization.serializer

import com.greentree.commons.serialization.annotation.CustomSerializer
import com.greentree.commons.serialization.descriptor.SerialDescriptor
import java.lang.reflect.Modifier
import kotlin.reflect.KClass

interface Serializer<T : Any> : SerializationStrategy<T>, DeserializationStrategy<T> {

	override val descriptor: SerialDescriptor<T>
}

inline fun <reified T : Any> serializer() = serializer(T::class)

fun <T : Any> serializer(cls: Class<T>) = serializer(cls.kotlin)

object SerializerCache {

	fun <T : Any> newInstance(cls: KClass<T>): T {
		return cls.objectInstance ?: run {
			val constructor = cls.java.getDeclaredConstructor()
			constructor.trySetAccessible()
			val instance = constructor.newInstance()
			instance
		}
	}
}

fun <T : Any> serializer(cls: KClass<T>): Serializer<T> =
	when {
		cls.java.isAnnotationPresent(CustomSerializer::class.java) -> SerializerCache.newInstance(
			cls.java.getDeclaredAnnotation(CustomSerializer::class.java)
				.serializator
		) as Serializer<T>
//		cls.java == java.lang.Long::class.java -> LongSerializer
//		cls.java == java.lang.Integer::class.java -> IntSerializer
//		cls.java == java.lang.Short::class.java -> ShortSerializer
//		cls.java == java.lang.Byte::class.java -> ByteSerializer
//		cls.java == java.lang.Double::class.java -> DoubleSerializer
//		cls.java == java.lang.Float::class.java -> FloatSerializer
//		cls.java == java.lang.String::class.java -> StringSerializer
//		cls.java == java.lang.Boolean::class.java -> BooleanSerializer
		cls.java.isEnum -> EnumSerializer(cls)
		Modifier.isFinal(cls.java.modifiers) -> FinalClassSerializer(cls.java)
		cls.java.isSealed -> ByteNameJavaSealedClassSerializer(cls.java)
		isKotlin(cls) && cls.isSealed -> when {
			cls.sealedSubclasses.size < Byte.VALUES -> ByteNameKotlinSealedClassSerializer(cls)
			else -> IntNameKotlinSealedClassSerializer(cls)
		}

		else -> BaseClassSerializer(cls.java)
	} as Serializer<T>

fun isKotlin(cls: KClass<*>): Boolean {
	return false
}

val Byte.Companion.VALUES
	get() = MAX_VALUE - MIN_VALUE + 1