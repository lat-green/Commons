package com.greentree.commons.serialization.serializator.provider

import com.greentree.commons.serialization.serializator.Serializator

interface SerializatorProvider {

	val priority: Int
		get() = 10

	fun <T : Any> provide(cls: Class<T>): Serializator<T>?
}