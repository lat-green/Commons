package com.greentree.commons.serialization.serializator

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeInfoBuilder.getTypeInfo
import com.greentree.commons.reflection.info.TypeUtil
import com.greentree.commons.serialization.context.EmptySerializationContext
import com.greentree.commons.serialization.context.SerializationContext
import com.greentree.commons.serialization.format.Decoder

interface DeserializationStrategy<out T : Any> {

	val type: TypeInfo<out T>
		get() = TypeUtil.getSuperType(
			getTypeInfo(this::class),
			DeserializationStrategy::class.java,
		).typeArguments[0] as TypeInfo<out T>

	fun deserialize(context: SerializationContext, decoder: Decoder): T
}

fun <T : Any> DeserializationStrategy<T>.deserialize(decoder: Decoder): T =
	deserialize(EmptySerializationContext, decoder)

