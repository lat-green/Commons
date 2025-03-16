package com.greentree.commons.serialization.serializator.type

import com.greentree.commons.serialization.context.SerializationContext
import com.greentree.commons.serialization.format.Decoder
import com.greentree.commons.serialization.format.Encoder

interface TypedSerializator {

	val priority: Int
		get() = 10

	fun isSupported(guaranteed: Class<*>): Boolean

	fun serialize(context: SerializationContext, encoder: Encoder, guaranteed: Class<*>, current: Class<*>)
	fun deserialize(context: SerializationContext, decoder: Decoder, guaranteed: Class<*>): Class<*>
}

