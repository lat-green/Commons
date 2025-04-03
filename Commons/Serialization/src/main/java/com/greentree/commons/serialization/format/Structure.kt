package com.greentree.commons.serialization.format

interface Structure<T> : AutoCloseable {

	fun field(name: String): T = fieldOrNull(name) ?: throw NullPointerException(name)
	fun field(index: Int): T = fieldOrNull(index) ?: throw NullPointerException("$index")

	fun fieldOrNull(name: String): T? = null
	fun fieldOrNull(index: Int): T?  = null

	override fun close() {
	}
}
