package com.greentree.commons.serialization.serializator.provider

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.serialization.context.SerializationContext
import com.greentree.commons.serialization.format.Decoder
import com.greentree.commons.serialization.format.Encoder
import com.greentree.commons.serialization.serializator.Serializator

private data object ObjectSerializator : Serializator<Any> {

	override fun serialize(context: SerializationContext, encoder: Encoder, value: Any) {
		encoder.beginStructure().use {
		}
	}

	override fun deserialize(context: SerializationContext, decoder: Decoder): Any =
		decoder.beginStructure().use {
			Any()
		}
}

data object ObjectSerializatorProvider : SerializatorProvider {

	override val priority: Int
		get() = 1

	override fun <T : Any> provide(type: TypeInfo<T>): Serializator<T>? {
		val cls = type.toClass()
		return if(cls == Any::class.java)
			ObjectSerializator as Serializator<T>
		else
			null
	}
}