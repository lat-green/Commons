package com.greentree.commons.data.resource.location

import com.greentree.commons.data.FileUtil
import com.greentree.commons.data.resource.FileResource
import com.greentree.commons.data.resource.IOResource
import com.greentree.commons.util.iterator.IteratorUtil
import java.io.File
import java.io.IOException
import java.nio.file.Path

class RootFileResourceLocation(val root: File) : NamedResourceLocation, IOResourceLocation {

	constructor(path: Path) : this(path.toFile())

	init {
		if(root.exists())
			require(root.isDirectory) { "root must by directory [root=$root]" }
		else
			root.mkdirs()
	}

	constructor(file: String) : this(File(file))

	override fun clear() {
		FileUtil.clearDir(root)
	}

	override fun getResource(name: String): IOResource {
		val f = File(root, name)
		if(f.exists())
			checkFile(f)
		return FileResource(f)
	}

	override fun isExist(name: String): Boolean {
		return File(root, name).exists()
	}

	private fun checkFile(file: File) {
		if(!file.isFile) throw RuntimeException("$file is not file")
	}

	override fun createNewResource(name: String): IOResource {
		val f = File(root, name)
		if(f.exists()) throw RuntimeException(f.toString() + "")
		f.parentFile.mkdirs()
		try {
			f.createNewFile()
		} catch(e: IOException) {
			throw RuntimeException("file:$f", e)
		}
		return FileResource(f)
	}

	override fun deleteResource(name: String): Boolean {
		val f = File(root, name)
		checkFile(f)
		return if(!f.exists()) false else f.delete()
	}

	override fun createResource(name: String): IOResource {
		val f = File(root, name)
		if(!f.exists()) {
			f.parentFile.mkdirs()
			try {
				f.createNewFile()
			} catch(e: IOException) {
				e.printStackTrace()
			}
		} else checkFile(f)
		return FileResource(f)
	}

	override val lastModified: Long
		get() = root.lastModified()
	override val names: Iterable<String>
		get() = IteratorUtil.iterable(*root.list())

	override fun toString(): String {
		return "FileSystemLocation[$root]"
	}

	companion object {

		private const val serialVersionUID = 1L
	}
}