package com.greentree.commons.serialization.serializer

import com.greentree.commons.serialization.data.Decoder
import com.greentree.commons.serialization.data.Encoder
import com.greentree.commons.serialization.descriptor.SerialDescriptor
import java.lang.reflect.Modifier
import kotlin.reflect.KClass

interface Serializer<T> : SerializationStrategy<T>, DeserializationStrategy<T> {

	override val descriptor: SerialDescriptor<T>

	override fun deserialize(decoder: Decoder): T
	override fun serialize(encoder: Encoder, value: T)
}

inline fun <reified T : Any> serializer() = serializer(T::class.java)

fun <T : Any> serializer(cls: KClass<T>) = serializer(cls.java)

fun <T : Any> serializer(cls: Class<T>) =
	when {
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