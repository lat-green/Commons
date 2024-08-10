package com.greentree.commons.serialization.serializer

import com.greentree.commons.serialization.data.Decoder
import com.greentree.commons.serialization.data.Encoder
import com.greentree.commons.serialization.descriptor.DoubleSerialDescriptor

data object DoubleSerializer : Serializer<Double> {

	override val descriptor = DoubleSerialDescriptor

	override fun deserialize(decoder: Decoder) = decoder.decodeDouble()
	override fun serialize(encoder: Encoder, value: Double) = encoder.encodeDouble(value)
}
