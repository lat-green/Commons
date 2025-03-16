package com.greentree.commons.serialization.serializator

import com.greentree.commons.serialization.context.EmptySerializationContext
import com.greentree.commons.serialization.context.SerializationContext
import com.greentree.commons.serialization.format.Encoder

interface SerializationStrategy<in T> {

	fun serialize(context: SerializationContext, encoder: Encoder, value: T)
}

fun <T> SerializationStrategy<T>.serialize(encoder: Encoder, value: T) =
	serialize(EmptySerializationContext, encoder, value)
