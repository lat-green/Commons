package com.greentree.commons.serialization.serializator

import com.greentree.commons.serialization.context.SerializationContext
import com.greentree.commons.serialization.format.Decoder
import com.greentree.commons.serialization.format.Encoder

object BooleanSerializator : Serializator<Boolean> {

	override fun serialize(context: SerializationContext, encoder: Encoder, value: Boolean) =
		encoder.encodeBoolean(value)

	override fun deserialize(context: SerializationContext, decoder: Decoder): Boolean = decoder.decodeBoolean()
}