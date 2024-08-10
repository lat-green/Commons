package com.greentree.commons.serialization.serializer

import com.greentree.commons.serialization.data.Decoder

interface DeserializationStrategy<out T> {

	fun deserialize(decoder: Decoder): T
}

