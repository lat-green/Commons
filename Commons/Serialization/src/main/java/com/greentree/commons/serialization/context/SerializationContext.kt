package com.greentree.commons.serialization.context

import com.greentree.commons.serialization.context.SerializationContext.*
import com.greentree.commons.serialization.serializator.manager.SerializatorManager

interface SerializationContext {

	operator fun <E : Element> get(key: Key<E>): E = getOrNull(key) ?: throw NullPointerException("$key")
	fun <E : Element> getOrNull(key: Key<E>): E?
	fun <R> fold(initial: R, operation: (R, Element) -> R): R
	fun minusKey(key: Key<*>): SerializationContext

	operator fun plus(context: SerializationContext): SerializationContext =
		if(context === EmptySerializationContext)
			this
		else
			context.fold(this) { acc, element ->
				val removed = acc.minusKey(element.key)
				if(removed === EmptySerializationContext)
					element
				else {
					CombinedContext(removed, element)
				}
			}

	interface Key<E : Element>

	interface Element : SerializationContext {

		val key: Key<*>

		override fun <E : Element> getOrNull(key: Key<E>): E? =
			@Suppress("UNCHECKED_CAST")
			if(this.key == key) this as E else null

		override fun <R> fold(initial: R, operation: (R, Element) -> R): R =
			operation(initial, this)

		override fun minusKey(key: Key<*>): SerializationContext =
			if(this.key == key) EmptySerializationContext else this
	}

	interface Property<T> : Element {

		val value: T
	}

	interface Marker<T : Marker<T>> : Element, Key<T> {

		override val key
			get() = this
	}
}

val SerializationContext.manager
	get() = get(SerializatorManager)

fun <T> SerializationContext.getProperty(key: Key<out Property<T>>) =
	get(key).value

fun <T> SerializationContext.getPropertyOrNull(key: Key<out Property<T>>) =
	getOrNull(key)?.value

operator fun SerializationContext.contains(key: Key<*>) = getOrNull(key) != null