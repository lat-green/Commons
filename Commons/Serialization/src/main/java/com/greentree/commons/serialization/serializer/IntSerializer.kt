package com.greentree.commons.serialization.serializer

import com.greentree.commons.serialization.data.Decoder
import com.greentree.commons.serialization.data.Encoder
import com.greentree.commons.serialization.descriptor.IntSerialDescriptor

data object IntSerializer : Serializer<Int> {

	override val descriptor = IntSerialDescriptor

	override fun deserialize(decoder: Decoder) = decoder.decodeInt()
	override fun serialize(encoder: Encoder, value: Int) = encoder.encodeInt(value)
}
