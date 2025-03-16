package com.greentree.commons.serialization.serializator

import com.greentree.commons.serialization.context.SerializationContext
import com.greentree.commons.serialization.context.getPropertyOrNull
import com.greentree.commons.serialization.format.Decoder
import com.greentree.commons.serialization.format.Encoder
import com.greentree.commons.serialization.serializator.accuracy.DoubleAccuracy
import com.greentree.commons.serialization.serializator.accuracy.calculator
import com.greentree.commons.serialization.serializator.accuracy.decode
import com.greentree.commons.serialization.serializator.accuracy.encode

object DoubleSerializator : Serializator<Double> {

	override fun serialize(context: SerializationContext, encoder: Encoder, value: Double) {
		val accuracy = context.getPropertyOrNull(DoubleAccuracy.Property) ?: return encoder.encodeDouble(value)
		accuracy.calculator.encode(encoder, value)
	}

	override fun deserialize(context: SerializationContext, decoder: Decoder): Double {
		val accuracy = context.getPropertyOrNull(DoubleAccuracy.Property) ?: return decoder.decodeDouble()
		return accuracy.calculator.decode(decoder)
	}
}