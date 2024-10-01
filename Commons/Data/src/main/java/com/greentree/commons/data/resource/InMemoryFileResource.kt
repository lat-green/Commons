package com.greentree.commons.data.resource

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.concurrent.locks.StampedLock

class InMemoryFileResource(
	override val name: String,
) : MutableFileResource {

	constructor(name: String, array: ByteArray) : this(name) {
		this.array = array
	}

	private var array: ByteArray? = null
	private val lock = StampedLock()
	private var lastModified: Long = System.currentTimeMillis()

	override fun createFile(): Boolean {
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

	override val parent: ParentResource
		get() = TODO("Not yet implemented")

	override fun exists() = array != null

	override fun lastModified() = lastModified

	override fun delete(): Boolean {
		if(array == null)
			return false
		array = null
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
		if(array != null) {
			if(other.array == null) return false
			if(!array.contentEquals(other.array)) return false
		} else if(other.array != null) return false

		return true
	}

	override fun hashCode(): Int {
		var result = name.hashCode()
		result = 31 * result + (array?.contentHashCode() ?: 0)
		return result
	}
}