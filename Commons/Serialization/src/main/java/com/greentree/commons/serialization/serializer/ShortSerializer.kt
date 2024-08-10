package com.greentree.commons.serialization.serializer

import com.greentree.commons.serialization.data.Decoder
import com.greentree.commons.serialization.data.Encoder
import com.greentree.commons.serialization.descriptor.ShortSerialDescriptor

object ShortSerializer : Serializer<Short> {

	override val descriptor = ShortSerialDescriptor

	override fun deserialize(decoder: Decoder) = decoder.decodeShort()
	override fun serialize(encoder: Encoder, value: Short) = encoder.encodeShort(value)
}
