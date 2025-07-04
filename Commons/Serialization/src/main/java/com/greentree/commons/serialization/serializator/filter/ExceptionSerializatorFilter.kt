package com.greentree.commons.serialization.serializator.filter

import com.greentree.commons.serialization.context.SerializationContext
import com.greentree.commons.serialization.format.Decoder
import com.greentree.commons.serialization.format.Encoder
import com.greentree.commons.serialization.serializator.Serializator

data object ExceptionSerializatorFilter : SerializatorFilter {

	override fun <T : Any> serialize(
		context: SerializationContext,
		encoder: Encoder,
		value: T,
		chain: Serializator<T>
	) {
		try {
			return chain.serialize(context, encoder, value)
		} catch(e: OnSerializeRuntimeException) {
			throw e
		} catch(e: Throwable) {
			throw OnSerializeRuntimeException(encoder, value, e)
		}
	}

	override fun <T : Any> deserialize(context: SerializationContext, decoder: Decoder, chain: Serializator<T>): T {
		try {
			return chain.deserialize(context, decoder)
		} catch(e: OnDeserializeRuntimeException) {
			throw e
		} catch(e: Throwable) {
			throw OnDeserializeRuntimeException(decoder, e)
		}
	}
}

class OnDeserializeRuntimeException(
	val decoder: Decoder,
	cause: Throwable,
) : RuntimeException("exception on deserialize $decoder", cause)

class OnSerializeRuntimeException(
	val encoder: Encoder,
	val value: Any?,
	cause: Throwable,
) : RuntimeException("exception on serialize $encoder $value", cause)