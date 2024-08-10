package com.greentree.commons.serialization.serializer

import com.greentree.commons.serialization.data.Decoder
import com.greentree.commons.serialization.data.Encoder
import com.greentree.commons.serialization.descriptor.ByteSerialDescriptor

data object ByteSerializer : Serializer<Byte> {

	override val descriptor = ByteSerialDescriptor

	override fun deserialize(decoder: Decoder) = decoder.decodeByte()
	override fun serialize(encoder: Encoder, value: Byte) = encoder.encodeByte(value)
}
