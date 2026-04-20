package com.greentree.commons.data.resource

sealed interface Resource {

	val name: String

	fun exists(): Boolean
	val isFile: Boolean
	val isDirectory: Boolean

	fun lastModified(): Long
}

val Resource.lastModified
	get() = lastModified()

fun Resource.walk(block: (file: FileResource) -> Unit) {
	return when(this) {
		is ParentResource -> for(child in children) {
			child.walk(block)
		}

		is FileResource -> block(this)
		else -> TODO("$this")
	}
}