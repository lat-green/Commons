package com.greentree.commons.data.resource

import java.io.InputStream
import java.io.OutputStream

data class NotFoundResource(
	override val name: String,
) : MutableFileResource, MutableFolderResource, MutableRootResource {

	private fun throwNotFound(): Nothing {
		throw ResourceNotFound(name)
	}

	override fun delete(): Boolean {
		throwNotFound()
	}

	override fun lastModified(): Long {
		throwNotFound()
	}

	override fun setLastModified(time: Long) {
		throwNotFound()
	}

	override fun createThisFile(): Boolean {
		throwNotFound()
	}

	override fun openWrite(): OutputStream {
		throwNotFound()
	}

	override val length: Long
		get() = throwNotFound()

	override fun open(): InputStream {
		throwNotFound()
	}

	override val parent: ParentResource
		get() = throwNotFound()

	override fun exists(): Boolean {
		return false
	}

	override val isFile
		get() = throwNotFound()
	override val isDirectory
		get() = throwNotFound()

	override fun createThisFolder(): Boolean {
		throwNotFound()
	}

	override fun getChildren(name: String): MutableChildResource {
		throwNotFound()
	}

	override val children: Iterable<ChildResource>
		get() = throwNotFound()
}