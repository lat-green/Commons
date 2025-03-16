package com.greentree.commons.serialization.serializator.filter

import com.greentree.commons.serialization.context.SerializationContext
import com.greentree.commons.serialization.format.Decoder
import com.greentree.commons.serialization.format.Encoder
import com.greentree.commons.serialization.serializator.Serializator

data object ExceptionSerializatorFilter : SerializatorFilter {

	override fun <T> serialize(context: SerializationContext, encoder: Encoder, value: T, chain: Serializator<T>) {
		try {
			return chain.serialize(context, encoder, value)
		} catch(e: Throwable) {
			throw RuntimeException("exception on serialize $encoder $value", e)
		}
	}

	override fun <T> deserialize(context: SerializationContext, decoder: Decoder, chain: Serializator<T>): T {
		try {
			return chain.deserialize(context, decoder)
		} catch(e: Throwable) {
			throw RuntimeException("exception on deserialize $decoder", e)
		}
	}
}
