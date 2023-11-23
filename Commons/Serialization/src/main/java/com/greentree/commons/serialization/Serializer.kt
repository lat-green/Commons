package com.greentree.commons.serialization

interface Serializer<T> : SerializationStrategy<T>, DeserializationStrategy<T> {

	override val descriptor: SerialDescriptor

	override fun deserialize(decoder: Decoder): T
	override fun serialize(encoder: Encoder, value: T)
}