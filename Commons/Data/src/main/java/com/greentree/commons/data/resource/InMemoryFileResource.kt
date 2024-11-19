package com.greentree.commons.data.resource

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class InMemoryFileResource(
	override val name: String,
	private var parentResource: InMemoryFolderResource? = null,
) : MutableFileResource, InMemoryResource {

	private var array: ByteArray? = null
	override val parent: ParentResource
		get() = parentResource ?: throw RuntimeException("parent not found")

	constructor(
		name: String,
		array: ByteArray,
		parentResource: InMemoryFolderResource? = null,
	) : this(name, parentResource) {
		this.array = array
	}

	private var lastModified: Long = System.currentTimeMillis()

	override fun createThisFile(): Boolean {
		if(exists())
			return false
		array = ByteArray(0)
		lastModified = System.currentTimeMillis()
		return true
	}

	override fun openWrite() = object : ByteArrayOutputStream() {
		override fun close() {
			super.close()
			array = toByteArray()
			lastModified = System.currentTimeMillis()
		}
	}

	override val length: Long
		get() = checkExists().size.toLong()

	override fun open() = ByteArrayInputStream(checkExists())

	private fun checkExists() = array ?: throw ResourceNotFound(name)

	override fun exists() = array != null

	override fun lastModified() = lastModified

	override fun delete(): Boolean {
		if(array == null)
			return false
		array = null
		parentResource?.removeChild(this)
		parentResource = null
		lastModified = System.currentTimeMillis()
		return true
	}

	override fun toString(): String {
		val array = array
		return if(array != null)
			"InMemoryFileResource(name='$name', array=[${array.joinToString(limit = 10)}])"
		else
			"InMemoryFileResource(name='$name')"
	}

	override fun equals(other: Any?): Boolean {
		if(this === other) return true
		if(javaClass != other?.javaClass) return false

		other as InMemoryFileResource

		if(name != other.name) return false
		if(parentResource != other.parentResource) return false

		return true
	}

	override fun hashCode(): Int {
		var result = name.hashCode()
		result = 31 * result + (parentResource?.hashCode() ?: 0)
		return result
	}
}