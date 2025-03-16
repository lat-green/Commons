package com.greentree.commons.serialization.serializator.filter

import com.greentree.commons.serialization.context.SerializationContext
import com.greentree.commons.serialization.format.Decoder
import com.greentree.commons.serialization.format.Encoder
import com.greentree.commons.serialization.serializator.Serializator

data class SerializatorFilterWrap<T>(
	val filter: SerializatorFilter,
	val origin: Serializator<T>,
) : Serializator<T> {

	override val type
		get() = origin.type

	override fun deserialize(context: SerializationContext, decoder: Decoder): T =
		filter.deserialize(context, decoder, origin)

	override fun serialize(context: SerializationContext, encoder: Encoder, value: T) =
		filter.serialize(context, encoder, value, origin)
}