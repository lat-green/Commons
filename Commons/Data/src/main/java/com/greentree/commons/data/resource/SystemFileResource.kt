package com.greentree.commons.data.resource

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.channels.FileChannel
import java.nio.file.Path

data class SystemFileResource(
	val file: File,
) : MutableFileResource, MutableFolderResource, MutableRootResource {

	val path: Path
		get() = file.toPath()

	override fun createFile() = file.createNewFile()

	override fun createFolder() = file.mkdirs()

	override fun openWrite() = FileOutputStream(file)

	override val length
		get() = file.length()

	override fun open() = FileInputStream(file)

	override fun openChannel(): FileChannel = FileChannel.open(path)

	override fun exists() = file.exists()

	override val parent
		get() = SystemFileResource(file.parentFile)
	override val name: String
		get() = file.name

	override fun lastModified() = file.lastModified()

	override val children
		get() = file.listFiles().map { SystemFileResource(it) }

	override fun getChildren(name: String) = SystemFileResource(File(file, name))

	override fun delete(): Boolean {
		return file.delete()
	}
}