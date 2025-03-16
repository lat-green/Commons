package com.greentree.commons.serialization.serializator.type

import com.greentree.commons.serialization.context.SerializationContext
import com.greentree.commons.serialization.format.Decoder
import com.greentree.commons.serialization.format.Encoder

data object KotlinObjectTypeSerializator : TypedSerializator {

	override fun isSupported(guaranteed: Class<*>) = guaranteed.kotlin.objectInstance != null

	override fun serialize(context: SerializationContext, encoder: Encoder, guaranteed: Class<*>, current: Class<*>) {
		require(guaranteed == current) { "$guaranteed != $current" }
	}

	override fun deserialize(context: SerializationContext, decoder: Decoder, guaranteed: Class<*>) = guaranteed
}