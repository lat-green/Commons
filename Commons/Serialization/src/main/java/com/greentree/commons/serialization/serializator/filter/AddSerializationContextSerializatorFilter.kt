package com.greentree.commons.serialization.serializator.filter

import com.greentree.commons.serialization.context.SerializationContext

data class AddSerializationContextSerializatorFilter(
	val addContext: SerializationContext,
) : ContextSerializatorFilter {

	override fun filter(context: SerializationContext) = context + addContext
}