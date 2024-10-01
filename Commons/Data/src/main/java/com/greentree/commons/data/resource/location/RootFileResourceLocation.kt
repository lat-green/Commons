package com.greentree.commons.data.resource.location

import com.greentree.commons.data.FileUtil
import com.greentree.commons.data.resource.MutableFileResource
import com.greentree.commons.data.resource.MutableFolderResource
import com.greentree.commons.data.resource.MutableResource
import com.greentree.commons.data.resource.Resources
import java.io.File
import java.nio.file.Path

class RootFileResourceLocation(val root: File) : NamedResourceLocation, MutableResourceLocation {

	init {
		if(root.exists())
			require(root.isDirectory) { "root must by directory [root=$root]" }
		else
			root.mkdirs()
	}

	constructor(file: String) : this(File(file))
	constructor(path: Path) : this(path.toFile())

	override fun getResourceOrNull(name: String): MutableResource {
		val file = File(root, name)
		return Resources.of(file)
	}

	override fun getFileResourceOrNull(name: String): MutableFileResource {
		val file = File(root, name)
		if(file.exists() && !file.isFile)
			throw RuntimeException("$file is not file")
		return Resources.of(file)
	}

	override fun getFolderResourceOrNull(name: String): MutableFolderResource {
		val file = File(root, name)
		if(file.exists() && !file.isDirectory)
			throw RuntimeException("$file is not file")
		return Resources.of(file)
	}

	override val names: Iterable<String>
		get() = FileUtil.getAllFile(root).map { it.name }
}