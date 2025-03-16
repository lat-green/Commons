package com.greentree.commons.serialization.serializator.type

import com.greentree.commons.serialization.context.SerializationContext
import java.lang.reflect.Modifier

data class GuaranteedType(override val value: Class<*>) : SerializationContext.Property<Class<*>> {

	init {
		require(!Modifier.isFinal(value.modifiers)) { "$value is final" }
	}

	override val key
		get() = Key

	companion object Key : SerializationContext.Key<GuaranteedType>
}