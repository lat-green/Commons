package com.greentree.commons.data.resource

import com.greentree.commons.action.observable.RunObservable
import com.greentree.commons.data.FileWatcher
import com.greentree.commons.data.channel.AsynchronousFileChannelAsyncByteChannel
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.channels.AsynchronousFileChannel
import java.nio.channels.FileChannel
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardOpenOption
import java.nio.file.attribute.FileAttribute

data class SystemFileResource(
	val file: File,
) : MutableFileResource, MutableFolderResource, MutableRootResource {

	val path: Path
		get() = file.toPath()

	override fun setLastModified(time: Long) {
		file.setLastModified(time)
	}

	override fun createThisFile() = file.createNewFile()

	override fun createThisFolder() = file.mkdirs()

	override fun openWrite() = FileOutputStream(file)

	override val length
		get() = file.length()

	override fun open() = FileInputStream(file)

	override fun openAsyncChannel() =
		AsynchronousFileChannelAsyncByteChannel(
			AsynchronousFileChannel.open(path, StandardOpenOption.READ)
		)

	override fun openWriteChannel(): FileChannel =
		FileChannel.open(path, StandardOpenOption.WRITE, StandardOpenOption.CREATE)

	override fun openChannel(): FileChannel =
		FileChannel.open(path, StandardOpenOption.READ)

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

	override val onCreate: RunObservable
		get() = FileWatcher.onFileCreate(file)
	override val onModify: RunObservable
		get() = FileWatcher.onFileModify(file)
	override val onDelete: RunObservable
		get() = FileWatcher.onFileDelete(file)

	companion object {

		fun crateTempResource(prefix: String, suffix: String, vararg attrs: FileAttribute<*>): SystemFileResource {
			val file = Files.createTempFile(prefix, suffix).toFile()
			file.deleteOnExit()
			return SystemFileResource(file)
		}
	}
}