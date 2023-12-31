package com.greentree.commons.serialization.serializer

import com.greentree.commons.serialization.annotation.CustomSerializer
import com.greentree.commons.serialization.descriptor.SerialDescriptor
import java.lang.reflect.Modifier
import kotlin.reflect.KClass

interface Serializer<T : Any> : SerializationStrategy<T>, DeserializationStrategy<T> {

	override val descriptor: SerialDescriptor<T>
}

inline fun <reified T : Any> serializer() = serializer(T::class.java)

fun <T : Any> serializer(cls: KClass<T>) = serializer(cls.java)

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

fun <T : Any> serializer(cls: Class<T>) =
	when {
		cls.isAnnotationPresent(CustomSerializer::class.java) -> SerializerCache.newInstance(
			cls.getDeclaredAnnotation(CustomSerializer::class.java)
				.serializator
		) as Serializer<T>

		Modifier.isFinal(cls.modifiers) -> FinalClassSerializer(cls)
		cls.isSealed -> ByteNameJavaSealedClassSerializer(cls)
		cls.kotlin.isSealed -> when {
			cls.kotlin.sealedSubclasses.size < Byte.VALUES -> ByteNameKotlinSealedClassSerializer(
				cls.kotlin
			)

			else -> IntNameKotlinSealedClassSerializer(cls.kotlin)
		}

		else -> BaseClassSerializer(cls)
	}

val Byte.Companion.VALUES
	get() = MAX_VALUE - MIN_VALUE + 1