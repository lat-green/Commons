package com.greentree.commons.serialization.context

object EmptySerializationContext : SerializationContext {

	override fun <R> fold(initial: R, operation: (R, SerializationContext.Element) -> R) = initial

	override fun <E : SerializationContext.Element> getOrNull(key: SerializationContext.Key<E>): E? = null

	override fun minusKey(key: SerializationContext.Key<*>) = this

	@Deprecated("to auto replace", ReplaceWith("context"))
	override fun plus(context: SerializationContext) = context
}