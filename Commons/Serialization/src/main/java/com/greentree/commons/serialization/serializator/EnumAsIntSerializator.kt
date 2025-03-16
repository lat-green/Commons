package com.greentree.commons.serialization.serializator

import com.greentree.commons.serialization.context.SerializationContext
import com.greentree.commons.serialization.format.Decoder
import com.greentree.commons.serialization.format.Encoder

data class EnumAsIntSerializator<E : Enum<E>>(
	override val type: Class<E>,
) : EnumSerializator<E> {

	override fun serialize(context: SerializationContext, encoder: Encoder, value: E) =
		encoder.encodeInt(value.ordinal)

	override fun deserialize(context: SerializationContext, decoder: Decoder): E =
		type.enumConstants[decoder.decodeInt()]
}