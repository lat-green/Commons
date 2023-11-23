package com.greentree.commons.serialization

interface SerializationStrategy<T> {

	val descriptor: SerialDescriptor
	
	fun serialize(encoder: Encoder, value: T)
}
