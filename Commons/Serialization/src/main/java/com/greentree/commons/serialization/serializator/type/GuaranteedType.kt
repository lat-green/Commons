package com.greentree.commons.serialization.serializator.type

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.serialization.context.SerializationContext

data class GuaranteedType<T>(
	override val value: TypeInfo<T>,
) : SerializationContext.Property<TypeInfo<T>> {

	override val key
		get() = Key

	companion object Key : SerializationContext.Key<GuaranteedType<*>>
}