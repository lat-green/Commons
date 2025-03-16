package com.greentree.commons.serialization.context

import com.greentree.commons.serialization.context.SerializationContext.*

data class CombinedContext(
	private val left: SerializationContext,
	private val element: Element,
) : SerializationContext {

	override fun <R> fold(initial: R, operation: (R, Element) -> R): R =
		operation(left.fold(initial, operation), element)

	override fun <E : Element> getOrNull(key: Key<E>): E? {
		var cur = this
		while(true) {
			cur.element.getOrNull(key)?.let { return it }
			val next = cur.left
			if(next is CombinedContext) {
				cur = next
			} else {
				return next.getOrNull(key)
			}
		}
	}

	override fun minusKey(key: Key<*>): SerializationContext {
		element.getOrNull(key)?.let { return left }
		val newLeft = left.minusKey(key)
		return when {
			newLeft === left -> this
			newLeft === EmptySerializationContext -> element
			else -> CombinedContext(newLeft, element)
		}
	}
}