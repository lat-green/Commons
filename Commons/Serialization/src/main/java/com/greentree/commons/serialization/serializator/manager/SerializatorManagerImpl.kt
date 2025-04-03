package com.greentree.commons.serialization.serializator.manager

import com.greentree.commons.reflection.ClassUtil
import com.greentree.commons.serialization.serializator.Serializator
import com.greentree.commons.serialization.serializator.filter.AddSerializationContextSerializatorFilter
import com.greentree.commons.serialization.serializator.filter.SerializatorFilter
import com.greentree.commons.serialization.serializator.filter.SerializatorFilterWrap
import com.greentree.commons.serialization.serializator.provider.SerializatorProvider
import com.greentree.engine.rex.serialization.serializator.provider.GuaranteedClassSerializator

class SerializatorManagerImpl(
	serializators: Sequence<Serializator<*>>,
	val providers: Sequence<SerializatorProvider>,
	val filters: Sequence<SerializatorFilter>,
) : SerializatorManager {

	private val serializators: MutableMap<Class<*>, Serializator<*>> =
		serializators.map { wrap(it) }.associateBy { it.type }.toMutableMap()
	private val realSerializators: MutableMap<Class<*>, Serializator<*>> =
		serializators.map { wrap(it) }.associateBy { it.type }.toMutableMap()

	override fun <T> serializator(
		guaranteed: Class<out T>
	): Serializator<T> =
		serializatorOrNull(
			providers + GuaranteedClassSerializator,
			serializators,
			guaranteed
		) ?: throw NullPointerException("not found serializator for $guaranteed")

	override fun <T> realSerializator(cls: Class<T>): Serializator<T> =
		serializatorOrNull(providers, realSerializators, cls)
			?: throw NullPointerException("not found serializator for $cls")

	private fun <T> serializatorOrNull(
		providers: Sequence<SerializatorProvider>,
		serializators: MutableMap<Class<*>, Serializator<*>>,
		guaranteed: Class<out T>
	): Serializator<T>? {
		val guaranteed = ClassUtil.getNotPrimitive(guaranteed)
		@Suppress("UNCHECKED_CAST")
		return serializators.getOrPutNotNull(guaranteed) {
			val origin = provide(providers, guaranteed)
			if(origin == null)
				null
			else
				wrap(origin)
		} as Serializator<T>?
	}

	private fun <T> wrap(
		serializator: Serializator<T>,
	): Serializator<T> {
		var result = filters.fold(serializator) { acc, serializatorFilter ->
			SerializatorFilterWrap(serializatorFilter, acc)
		}
		result = SerializatorFilterWrap(
			AddSerializationContextSerializatorFilter(this),
			result
		)
		return result
	}

	companion object {

		private fun <T : Any> provide(
			providers: Sequence<SerializatorProvider>,
			cls: Class<T>,
		): Serializator<T>? {
			return providers
				.map { it to it.provide(cls) }
				.filter { it.second != null }
				.sortedBy { -it.first.priority }
				.map { it.second }
				.firstOrNull()
		}
	}
}

inline fun <K, V> MutableMap<K, V>.getOrPutNotNull(key: K, defaultValue: () -> V?): V? {
	val value = get(key)
	return if(value == null) {
		val answer = defaultValue()
		if(answer != null)
			put(key, answer)
		answer
	} else {
		value
	}
}