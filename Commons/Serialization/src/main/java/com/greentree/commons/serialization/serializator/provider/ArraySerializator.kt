package com.greentree.commons.serialization.serializator.provider

import com.greentree.commons.serialization.context.SerializationContext
import com.greentree.commons.serialization.context.manager
import com.greentree.commons.serialization.format.Decoder
import com.greentree.commons.serialization.format.Encoder
import com.greentree.commons.serialization.serializator.Serializator

data class ArraySerializator<T : Any>(
	val componentType: Class<T>,
) : Serializator<Array<T>> {

	override fun deserialize(context: SerializationContext, decoder: Decoder): Array<T> {
		val serializator = context.manager.serializator(componentType)
		return decoder.decodeArray(serializator)
	}

	override fun serialize(context: SerializationContext, encoder: Encoder, value: Array<T>) {
		val serializator = context.manager.serializator(componentType)
		encoder.encodeArray(serializator, value)
	}

	override val type: Class<Array<T>>
		get() = Array::class.java as Class<Array<T>>

	companion object : SerializatorProvider {

		override fun <T : Any> provide(cls: Class<T>): Serializator<T>? {
			val guaranteed = cls.kotlin.java
			if(!guaranteed.isArray)
				return null
			val componentType = guaranteed.componentType
			if(componentType.isPrimitive)
				return null
			return ArraySerializator(guaranteed.componentType) as Serializator<T>
		}
	}
}