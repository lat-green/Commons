package com.greentree.commons.serialization.serializer

import com.greentree.commons.serialization.data.Decoder
import com.greentree.commons.serialization.data.Encoder
import com.greentree.commons.serialization.descriptor.StringSerialDescriptor

object StringSerializer : Serializer<String> {

	override val descriptor = StringSerialDescriptor

	override fun deserialize(decoder: Decoder): String {
		return decoder.decodeString()
	}

	override fun serialize(encoder: Encoder, value: String) {
		encoder.encodeString(value)
	}
}
