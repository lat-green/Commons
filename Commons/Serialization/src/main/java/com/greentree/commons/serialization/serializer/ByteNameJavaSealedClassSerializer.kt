package com.greentree.commons.serialization.serializer

import com.greentree.commons.serialization.data.Decoder
import com.greentree.commons.serialization.data.Encoder
import com.greentree.commons.serialization.data.decodeSerializable
import com.greentree.commons.serialization.data.encodeSerializable
import com.greentree.commons.serialization.descriptor.ByteSerialDescriptor
import com.greentree.commons.serialization.descriptor.SerialDescriptor
import com.greentree.commons.serialization.descriptor.descriptor
import kotlin.reflect.KClass

class ByteNameJavaSealedClassSerializer<T : Any>(private val cls: Class<T>) : Serializer<T> {

	init {
		require(cls.isSealed) { "$cls" }
	}

	override val descriptor: SerialDescriptor<T>
		get() = SerialDescriptor.builder("type-value")
			.element("t", ByteSerialDescriptor)
			.element("v", cls.descriptor)
			.build()

	override fun deserialize(decoder: Decoder): T {
		val sealedSubclasses = cls.permittedSubclasses
		if(sealedSubclasses.size == 1)
			return serializer(sealedSubclasses.first() as Class<T>).deserialize(decoder)
		return decoder.beginStructure(descriptor).use { struct ->
			val ordinal = struct.field(0).decodeByte().toInt()
			struct.field(1).decodeSerializable(sealedSubclasses[ordinal] as Class<T>)
		}
	}

	override fun serialize(encoder: Encoder, value: T) {
		val sealedSubclasses = cls.permittedSubclasses
		if(sealedSubclasses.size == 1)
			return serializer(sealedSubclasses.first() as Class<T>).serialize(encoder, value)
		val ordinal = sealedSubclasses.indexOf(value::class.java).toByte()
		encoder.beginStructure(descriptor).use { struct ->
			struct.field(0).encodeByte(ordinal)
			struct.field(1).encodeSerializable(value)
		}
	}
}