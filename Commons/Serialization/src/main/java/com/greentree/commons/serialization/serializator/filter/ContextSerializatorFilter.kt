package com.greentree.commons.serialization.serializator.filter

import com.greentree.commons.serialization.context.SerializationContext
import com.greentree.commons.serialization.format.Decoder
import com.greentree.commons.serialization.format.Encoder
import com.greentree.commons.serialization.serializator.Serializator

fun interface ContextSerializatorFilter : SerializatorFilter {

	override fun <T> serialize(context: SerializationContext, encoder: Encoder, value: T, chain: Serializator<T>) =
		chain.serialize(filter(context), encoder, value)

	override fun <T> deserialize(context: SerializationContext, decoder: Decoder, chain: Serializator<T>) =
		chain.deserialize(filter(context), decoder)

	fun filter(context: SerializationContext): SerializationContext
}