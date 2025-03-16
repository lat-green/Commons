package com.greentree.commons.serialization.serializator

import com.greentree.commons.serialization.context.SerializationContext
import com.greentree.commons.serialization.context.getPropertyOrNull
import com.greentree.commons.serialization.format.Decoder
import com.greentree.commons.serialization.format.Encoder
import com.greentree.commons.serialization.serializator.accuracy.IntAccuracy
import com.greentree.commons.serialization.serializator.accuracy.calculator
import com.greentree.commons.serialization.serializator.accuracy.decode
import com.greentree.commons.serialization.serializator.accuracy.encode

object IntSerializator : Serializator<Int> {

	override fun serialize(context: SerializationContext, encoder: Encoder, value: Int) {
		val accuracy = context.getPropertyOrNull(IntAccuracy.Property) ?: return encoder.encodeInt(value)
		accuracy.calculator.encode(encoder, value)
	}

	override fun deserialize(context: SerializationContext, decoder: Decoder): Int {
		val accuracy = context.getPropertyOrNull(IntAccuracy.Property) ?: return decoder.decodeInt()
		return accuracy.calculator.decode(decoder)
	}
}