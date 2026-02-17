package com.greentree.commons.serialization.format

interface FieldGroup<K, out T : Any> : AutoCloseable {

	fun field(name: K): T = fieldOrNull(name) ?: throw NullPointerException("name: '$name'")

	fun fieldOrNull(name: K): T?

	override fun close() {
	}
}

interface StructureFieldGroup<out T : Any> : FieldGroup<String, T>

interface CollectionFieldGroup<out T : Any> : FieldGroup<Int, T> {

	override fun field(index: Int) = super.field(index)
	override fun fieldOrNull(index: Int): T?
}

interface NamedStructure<K, out T : Any> : FieldGroup<K, T> {

	val keys: Set<K>
}

interface NamedStructureFieldGroup<out T : Any> : NamedStructure<String, T>, StructureFieldGroup<T>

interface NamedCollectionFieldGroup<out T : Any> : NamedStructure<Int, T>, CollectionFieldGroup<T>

data class StructureFieldGroupImpl<out T : Any>(val original: T) : StructureFieldGroup<T> {

	override fun fieldOrNull(name: String) = original
}

data class CollectionFieldGroupImpl<out T : Any>(val original: T) : CollectionFieldGroup<T> {

	override fun fieldOrNull(index: Int) = original
}