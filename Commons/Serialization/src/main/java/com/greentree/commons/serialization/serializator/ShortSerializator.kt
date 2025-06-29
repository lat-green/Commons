package com.greentree.commons.serialization.serializator

import com.greentree.commons.serialization.context.SerializationContext
import com.greentree.commons.serialization.context.getPropertyOrNull
import com.greentree.commons.serialization.format.Decoder
import com.greentree.commons.serialization.format.Encoder
import com.greentree.commons.serialization.serializator.accuracy.ShortAccuracy
import com.greentree.commons.serialization.serializator.accuracy.calculator
import com.greentree.commons.serialization.serializator.accuracy.decode
import com.greentree.commons.serialization.serializator.accuracy.encode

object ShortSerializator : Serializator<Short> {

	override fun serialize(context: SerializationContext, encoder: Encoder, value: Short) {
		val accuracy = context.getPropertyOrNull(ShortAccuracy.Property) ?: return encoder.encodeShort(value)
		accuracy.calculator.encode(encoder, value)
	}

	override fun deserialize(context: SerializationContext, decoder: Decoder): Short {
		val accuracy = context.getPropertyOrNull(ShortAccuracy.Property) ?: return decoder.decodeShort()
		return accuracy.calculator.decode(decoder)
	}
}