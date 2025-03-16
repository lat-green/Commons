package com.greentree.commons.serialization.serializator

import com.greentree.commons.serialization.context.SerializationContext
import com.greentree.commons.serialization.format.Decoder
import com.greentree.commons.serialization.format.Encoder

data object IntArraySerializator : Serializator<IntArray> {

	override fun deserialize(context: SerializationContext, decoder: Decoder): IntArray {
		return decoder.decodeIntArray()
	}

	override fun serialize(context: SerializationContext, encoder: Encoder, value: IntArray) {
		encoder.encodeIntArray(value)
	}
}