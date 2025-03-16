package com.greentree.commons.serialization.serializator

import com.greentree.commons.serialization.context.SerializationContext
import com.greentree.commons.serialization.context.getPropertyOrNull
import com.greentree.commons.serialization.format.Decoder
import com.greentree.commons.serialization.format.Encoder
import com.greentree.commons.serialization.serializator.accuracy.LongAccuracy
import com.greentree.commons.serialization.serializator.accuracy.calculator
import com.greentree.commons.serialization.serializator.accuracy.decode
import com.greentree.commons.serialization.serializator.accuracy.encode

object LongSerializator : Serializator<Long> {

	override fun serialize(context: SerializationContext, encoder: Encoder, value: Long) {
		val accuracy = context.getPropertyOrNull(LongAccuracy.Property) ?: return encoder.encodeLong(value)
		accuracy.calculator.encode(encoder, value)
	}

	override fun deserialize(context: SerializationContext, decoder: Decoder): Long {
		val accuracy = context.getPropertyOrNull(LongAccuracy.Property) ?: return decoder.decodeLong()
		return accuracy.calculator.decode(decoder)
	}
}