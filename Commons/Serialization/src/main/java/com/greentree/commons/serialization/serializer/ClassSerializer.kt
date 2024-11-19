package com.greentree.commons.serialization.serializer

import com.greentree.commons.serialization.data.Decoder
import com.greentree.commons.serialization.data.Encoder
import com.greentree.commons.serialization.descriptor.ClassSerialDescriptor

object ClassSerializer : Serializer<Class<*>> {

	override val descriptor = ClassSerialDescriptor

	override fun deserialize(decoder: Decoder): Class<*> {
		return Class.forName(decoder.decodeString())
	}

	override fun serialize(encoder: Encoder, value: Class<*>) {
		encoder.encodeString(value.name)
	}
}
