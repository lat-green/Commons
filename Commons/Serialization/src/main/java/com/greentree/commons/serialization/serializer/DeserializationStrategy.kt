package com.greentree.commons.serialization.serializer

import com.greentree.commons.serialization.Decoder

interface DeserializationStrategy<T> {

	fun deserialize(decoder: Decoder): T
}
