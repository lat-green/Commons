package com.greentree.commons.serialization.serializer

import com.greentree.commons.serialization.data.Decoder
import com.greentree.commons.serialization.data.Encoder
import com.greentree.commons.serialization.descriptor.SerialDescriptor
import com.greentree.commons.serialization.descriptor.StringSerialDescriptor
import com.greentree.commons.serialization.descriptor.descriptor

data class BaseClassSerializer<T : Any>(private val cls: Class<T>) : Serializer<T> {

	override val descriptor = SerialDescriptor.builder("type-value")
		.element("type", StringSerialDescriptor)
		.element("value", cls.descriptor)
		.build()

	override fun deserialize(decoder: Decoder): T {
		decoder.beginStructure(descriptor).use { struct ->
			val type = struct.field(0).decodeString()
			val cls = Class.forName(type) as Class<T>
//			return struct.field(1).decodeSerializable(cls)
			return serializerFinal(cls).deserialize(struct.field(1))
		}
	}

	override fun serialize(encoder: Encoder, value: T) {
		encoder.beginStructure(descriptor).use { struct ->
			struct.field(0).encodeString(value::class.java.name)
//			struct.field(1).encodeSerializable(value)
			serializerFinal(value.javaClass).serialize(struct.field(1), value)
		}
	}
}
