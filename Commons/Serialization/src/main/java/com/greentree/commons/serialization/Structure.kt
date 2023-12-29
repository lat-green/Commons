package com.greentree.commons.serialization

interface Structure<T> : AutoCloseable {

	fun field(name: String): T
	fun field(index: Int): T

	override fun close() {
	}
}
