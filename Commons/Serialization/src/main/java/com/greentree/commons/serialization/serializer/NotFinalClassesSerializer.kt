package com.greentree.commons.serialization.serializer

import com.greentree.commons.serialization.data.Decoder
import com.greentree.commons.serialization.data.Encoder
import com.greentree.commons.serialization.data.decodeSerializable
import com.greentree.commons.serialization.data.encodeSerializable
import com.greentree.commons.serialization.descriptor.SerialDescriptor
import com.greentree.commons.serialization.descriptor.StringSerialDescriptor
import com.greentree.commons.serialization.descriptor.descriptor
import java.lang.reflect.Modifier

class NotFinalClassesSerializer<T : Any>(private val cls: Class<T>) : Serializer<T> {

	init {
		require(!Modifier.isFinal(cls.modifiers)) { "type $cls is final" }
	}

	override val descriptor: SerialDescriptor<T>
		get() = SerialDescriptor.builder("type-value")
			.element("t", StringSerialDescriptor)
			.element("v", cls.descriptor)
			.build()

	override fun deserialize(decoder: Decoder): T {
		decoder.beginStructure(descriptor).use { struct ->
			val type = struct.field(0).decodeString()
			val cls = Class.forName(type) as Class<T>
			return struct.field(1).decodeSerializable(cls)
		}
	}

	override fun serialize(encoder: Encoder, value: T) {
		encoder.beginStructure(descriptor).use { struct ->
			struct.field(0).encodeString(value::class.java.name)
			struct.field(1).encodeSerializable(value)
		}
	}
}
