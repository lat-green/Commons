package com.greentree.commons.serialization.serializator

import com.greentree.commons.reflection.info.TypeUtil
import com.greentree.commons.serialization.context.EmptySerializationContext
import com.greentree.commons.serialization.context.SerializationContext
import com.greentree.commons.serialization.format.Decoder

interface DeserializationStrategy<out T> {

	val type: Class<out T>
		get() = TypeUtil.getFirstArgument(
			this::class,
			DeserializationStrategy::class
		)

	fun deserialize(context: SerializationContext, decoder: Decoder): T
}

fun <T> DeserializationStrategy<T>.deserialize(decoder: Decoder): T = deserialize(EmptySerializationContext, decoder)

