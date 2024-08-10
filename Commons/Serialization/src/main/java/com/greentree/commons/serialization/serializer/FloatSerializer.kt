package com.greentree.commons.serialization.serializer

import com.greentree.commons.serialization.data.Decoder
import com.greentree.commons.serialization.data.Encoder
import com.greentree.commons.serialization.descriptor.FloatSerialDescriptor

data object FloatSerializer : Serializer<Float> {

	override val descriptor = FloatSerialDescriptor

	override fun deserialize(decoder: Decoder) = decoder.decodeFloat()
	override fun serialize(encoder: Encoder, value: Float) = encoder.encodeFloat(value)
}
