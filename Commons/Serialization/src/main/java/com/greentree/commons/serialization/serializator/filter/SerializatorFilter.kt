package com.greentree.commons.serialization.serializator.filter

import com.greentree.commons.serialization.context.SerializationContext
import com.greentree.commons.serialization.format.Decoder
import com.greentree.commons.serialization.format.Encoder
import com.greentree.commons.serialization.serializator.Serializator

interface SerializatorFilter {

	fun <T> serialize(context: SerializationContext, encoder: Encoder, value: T, chain: Serializator<T>)

	fun <T> deserialize(context: SerializationContext, decoder: Decoder, chain: Serializator<T>): T
}

