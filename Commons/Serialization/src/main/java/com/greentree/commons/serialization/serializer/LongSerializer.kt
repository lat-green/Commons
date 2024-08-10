package com.greentree.commons.serialization.serializer

import com.greentree.commons.serialization.data.Decoder
import com.greentree.commons.serialization.data.Encoder
import com.greentree.commons.serialization.descriptor.LongSerialDescriptor

data object LongSerializer : Serializer<Long> {

	override val descriptor = LongSerialDescriptor

	override fun deserialize(decoder: Decoder) = decoder.decodeLong()
	override fun serialize(encoder: Encoder, value: Long) = encoder.encodeLong(value)
}
