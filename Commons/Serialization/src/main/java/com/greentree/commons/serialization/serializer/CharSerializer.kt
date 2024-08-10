package com.greentree.commons.serialization.serializer

import com.greentree.commons.serialization.data.Decoder
import com.greentree.commons.serialization.data.Encoder
import com.greentree.commons.serialization.descriptor.CharSerialDescriptor

data object CharSerializer : Serializer<Char> {

	override val descriptor = CharSerialDescriptor

	override fun deserialize(decoder: Decoder) = decoder.decodeChar()
	override fun serialize(encoder: Encoder, value: Char) = encoder.encodeChar(value)
}
