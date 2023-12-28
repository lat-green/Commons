package com.greentree.commons.serialization.serializer

import com.greentree.commons.serialization.Decoder
import com.greentree.commons.serialization.Encoder
import com.greentree.commons.serialization.descriptor.SerialDescriptor
import java.lang.reflect.Modifier

interface Serializer<T> : SerializationStrategy<T>, DeserializationStrategy<T> {

	override val descriptor: SerialDescriptor<T>

	override fun deserialize(decoder: Decoder): T
	override fun serialize(encoder: Encoder, value: T)
}

inline fun <reified T : Any> serializer() = serializer(T::class.java)

fun <T : Any> serializer(cls: Class<T>) =
	if(Modifier.isFinal(cls.modifiers))
		ReflectionSerializer(cls)
	else
		NotFinalClassesSerializer(cls)