package com.greentree.commons.serialization.serializator

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeInfoBuilder.getTypeInfo
import com.greentree.commons.reflection.info.TypeUtil
import com.greentree.commons.serialization.context.SerializationContext
import com.greentree.commons.serialization.format.Decoder
import com.greentree.commons.serialization.format.Encoder

interface Serializator<T : Any> : SerializationStrategy<T>, DeserializationStrategy<T> {

	override val type: TypeInfo<out T>
		get() = TypeUtil.getSuperType(
			getTypeInfo(this::class),
			Serializator::class.java,
		).typeArguments[0] as TypeInfo<out T>

	override fun serialize(context: SerializationContext, encoder: Encoder, value: T)
	override fun deserialize(context: SerializationContext, decoder: Decoder): T
}