package com.greentree.commons.serialization

interface DeserializationStrategy<T> {

	fun deserialize(decoder: Decoder): T
}
