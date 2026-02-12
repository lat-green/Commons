package com.greentree.commons.serialization.serializator.type

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.serialization.context.SerializationContext
import java.lang.reflect.Modifier

data class GuaranteedType(override val value: TypeInfo<*>) : SerializationContext.Property<TypeInfo<*>> {

	init {
		require(!Modifier.isFinal(value.modifiers)) { "$value is final" }
	}

	override val key
		get() = Key

	companion object Key : SerializationContext.Key<GuaranteedType>
}