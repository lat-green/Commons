package com.greentree.commons.serialization.serializator

import com.greentree.commons.serialization.context.SerializationContext
import com.greentree.commons.serialization.format.Decoder
import com.greentree.commons.serialization.format.Encoder

object ByteSerializator : Serializator<Byte> {

	override fun serialize(context: SerializationContext, encoder: Encoder, value: Byte) = encoder.encodeByte(value)
	override fun deserialize(context: SerializationContext, decoder: Decoder): Byte = decoder.decodeByte()
}