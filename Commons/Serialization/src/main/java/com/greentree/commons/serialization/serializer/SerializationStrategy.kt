package com.greentree.commons.serialization.serializer

import com.greentree.commons.serialization.data.Encoder
import com.greentree.commons.serialization.descriptor.SerialDescriptor

interface SerializationStrategy<T> {

	val descriptor: SerialDescriptor<T>

	fun serialize(encoder: Encoder, value: T)
}
