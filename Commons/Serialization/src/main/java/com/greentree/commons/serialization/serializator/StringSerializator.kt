package com.greentree.commons.serialization.serializator

import com.greentree.commons.serialization.context.SerializationContext
import com.greentree.commons.serialization.format.Decoder
import com.greentree.commons.serialization.format.Encoder
import com.greentree.commons.serialization.serializator.Serializator

object StringSerializator : Serializator<String> {

	override fun serialize(context: SerializationContext, encoder: Encoder, value: String) = encoder.encodeString(value)
	override fun deserialize(context: SerializationContext, decoder: Decoder): String = decoder.decodeString()
}