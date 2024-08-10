package com.greentree.commons.serialization.serializer

import com.greentree.commons.serialization.data.Decoder
import com.greentree.commons.serialization.data.Encoder
import com.greentree.commons.serialization.descriptor.EnumSerialDescriptor
import com.greentree.commons.serialization.descriptor.SerialDescriptor
import kotlin.reflect.KClass

class EnumSerializer<T : Enum<T>>(
	val cls: KClass<T>
) : Serializer<T> {

	override val descriptor = EnumSerialDescriptor(cls.java)

	override fun deserialize(decoder: Decoder): T {
		val name = decoder.decodeString()
		return cls.java.enumConstants.first { it.name == name }
	}

	override fun serialize(encoder: Encoder, value: T) {
		encoder.encodeString(value.name)
	}
}

fun <T : Any> EnumSerializer(cls: KClass<T>) = EnumSerializer(cls as KClass<out Enum<*>>)
