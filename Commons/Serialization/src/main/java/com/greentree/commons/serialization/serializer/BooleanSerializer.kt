package com.greentree.commons.serialization.serializer

import com.greentree.commons.serialization.data.Decoder
import com.greentree.commons.serialization.data.Encoder
import com.greentree.commons.serialization.descriptor.BooleanSerialDescriptor

data object BooleanSerializer : Serializer<Boolean> {

	override val descriptor = BooleanSerialDescriptor

	override fun deserialize(decoder: Decoder) = decoder.decodeBoolean()
	override fun serialize(encoder: Encoder, value: Boolean) = encoder.encodeBoolean(value)
}
