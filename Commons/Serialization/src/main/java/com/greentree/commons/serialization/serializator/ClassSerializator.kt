package com.greentree.commons.serialization.serializator

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.serialization.context.SerializationContext
import com.greentree.commons.serialization.context.getPropertyOrNull
import com.greentree.commons.serialization.format.Decoder
import com.greentree.commons.serialization.format.Encoder
import com.greentree.commons.serialization.serializator.type.GuaranteedType
import com.greentree.commons.serialization.serializator.type.TypedSerializator
import java.util.*

data class ClassSerializator(
	val typeSerializators: Sequence<TypedSerializator>,
) : Serializator<Class<*>> {

	private val cache = WeakHashMap<Class<*>, TypedSerializator>()

	private fun serializator(guaranteedType: Class<*>): TypedSerializator = cache.getOrPut(guaranteedType) {
		typeSerializators.filter { it.isSupported(guaranteedType) }.sortedBy { -it.priority }.first()
	}

	override fun serialize(context: SerializationContext, encoder: Encoder, value: Class<*>) {
		val guaranteedType = context.getPropertyOrNull(GuaranteedType) ?: TypeInfo(Any::class)
		val guaranteedClass = guaranteedType.toClass()
		val serializator = serializator(guaranteedClass)
		serializator.serialize(context, encoder, guaranteedClass, value)
	}

	override fun deserialize(context: SerializationContext, decoder: Decoder): Class<*> {
		val guaranteedType = context.getPropertyOrNull(GuaranteedType) ?: TypeInfo(Any::class)
		val guaranteedClass = guaranteedType.toClass()
		val serializator = serializator(guaranteedClass)
		return serializator.deserialize(context, decoder, guaranteedClass)
	}
}