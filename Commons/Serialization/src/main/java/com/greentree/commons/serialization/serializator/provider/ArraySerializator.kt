package com.greentree.commons.serialization.serializator.provider

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeInfoBuilder.getTypeInfo
import com.greentree.commons.serialization.context.SerializationContext
import com.greentree.commons.serialization.context.manager
import com.greentree.commons.serialization.format.Decoder
import com.greentree.commons.serialization.format.Encoder
import com.greentree.commons.serialization.serializator.Serializator
import com.greentree.commons.serialization.serializator.manager.serializator

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

	override val type: TypeInfo<Array<T>>
		get() = getTypeInfo(Array::class) as TypeInfo<Array<T>>

	companion object : SerializatorProvider {

		override fun <T : Any> provide(type: TypeInfo<T>): Serializator<T>? {
			val cls = type.toClass()
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