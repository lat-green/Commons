package com.greentree.commons.serialization.serializator.type

import com.greentree.commons.serialization.context.SerializationContext
import com.greentree.commons.serialization.format.Decoder
import com.greentree.commons.serialization.format.Encoder

object ClassAsNameSerializator : TypedSerializator {

	override val priority: Int
		get() = 0

	override fun isSupported(guaranteed: Class<*>) = true

	override fun serialize(context: SerializationContext, encoder: Encoder, guaranteed: Class<*>, current: Class<*>) =
		encoder.encodeClass(current)

	override fun deserialize(context: SerializationContext, decoder: Decoder, guaranteed: Class<*>): Class<*> =
		decoder.decodeClass()
}
