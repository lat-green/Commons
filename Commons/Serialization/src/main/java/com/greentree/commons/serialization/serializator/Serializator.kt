package com.greentree.commons.serialization.serializator

import com.greentree.commons.reflection.info.TypeUtil
import com.greentree.commons.serialization.context.SerializationContext
import com.greentree.commons.serialization.format.Decoder
import com.greentree.commons.serialization.format.Encoder

interface Serializator<T : Any> : SerializationStrategy<T>, DeserializationStrategy<T> {

	override val type: Class<out T>
		get() = TypeUtil.getFirstArgument(this::class, Serializator::class)

	override fun serialize(context: SerializationContext, encoder: Encoder, value: T)
	override fun deserialize(context: SerializationContext, decoder: Decoder): T
}