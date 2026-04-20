package com.greentree.commons.data.resource

import java.io.InputStream
import java.io.OutputStream

data class InMemoryProxyMutableResource(
	override val name: String,
) : MutableFolderResource, MutableFileResource, InMemoryResource {

	private var o: InMemoryResource? = null
	private val origin
		get() = o ?: throw ResourceNotFound(name)
	private val originFile
		get() = origin as InMemoryFileResource
	private val originFolder
		get() = origin as InMemoryFolderResource

	override fun createThisFolder(): Boolean {
		if(o != null)
			return false
		o = InMemoryFolderResource(name, listOf())
		return true
	}

	override fun getChildren(name: String) = originFolder.getChildren(name)

	override val isFile: Boolean
		get() = o is InMemoryFileResource
	override val isDirectory: Boolean
		get() = o is InMemoryFolderResource
	override val parent: ParentResource
		get() = TODO("Not yet implemented")

	override fun exists(): Boolean {
		val o = o
		return o != null && o.exists()
	}

	override fun lastModified() = origin.lastModified()

	override val children
		get() = originFolder.children

	override fun delete(): Boolean {
		return origin.delete()
	}

	override fun setLastModified(time: Long) {
		return origin.setLastModified(time)
	}

	override fun createThisFile(): Boolean {
		if(o != null)
			return false
		o = InMemoryFileResource(name)
		return true
	}

	override fun openWrite(): OutputStream {
		return originFile.openWrite()
	}

	override val length: Long
		get() = originFile.length

	override fun open(): InputStream {
		return originFile.open()
	}
}