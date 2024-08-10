package com.greentree.commons.serialization.serializer

import com.greentree.commons.serialization.data.Decoder
import com.greentree.commons.serialization.data.Encoder
import com.greentree.commons.serialization.descriptor.KotlinObjectSerialDescriptor
import kotlin.reflect.KClass

data class KotlinObjectSerializer<T : Any>(
	val cls: KClass<T>
) : Serializer<T> {

	private val objectInstance = cls.objectInstance!!
	override val descriptor = KotlinObjectSerialDescriptor(cls)

	override fun deserialize(decoder: Decoder): T = objectInstance

	override fun serialize(encoder: Encoder, value: T) {
		encoder.beginStructure(descriptor).use {
		}
	}
}
