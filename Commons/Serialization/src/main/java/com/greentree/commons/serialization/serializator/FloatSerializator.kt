package com.greentree.commons.serialization.serializator

import com.greentree.commons.serialization.context.SerializationContext
import com.greentree.commons.serialization.context.getPropertyOrNull
import com.greentree.commons.serialization.format.Decoder
import com.greentree.commons.serialization.format.Encoder
import com.greentree.commons.serialization.serializator.accuracy.FloatAccuracy
import com.greentree.commons.serialization.serializator.accuracy.calculator
import com.greentree.commons.serialization.serializator.accuracy.decode
import com.greentree.commons.serialization.serializator.accuracy.encode

object FloatSerializator : Serializator<Float> {

	override fun serialize(context: SerializationContext, encoder: Encoder, value: Float) {
		val accuracy = context.getPropertyOrNull(FloatAccuracy.Property) ?: return encoder.encodeFloat(value)
		accuracy.calculator.encode(encoder, value)
	}

	override fun deserialize(context: SerializationContext, decoder: Decoder): Float {
		val accuracy = context.getPropertyOrNull(FloatAccuracy.Property) ?: return decoder.decodeFloat()
		return accuracy.calculator.decode(decoder)
	}
}