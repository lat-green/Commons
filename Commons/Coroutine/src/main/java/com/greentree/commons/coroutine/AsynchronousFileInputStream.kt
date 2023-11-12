package com.greentree.commons.coroutine

import java.io.InputStream
import java.nio.ByteBuffer
import java.nio.channels.AsynchronousFileChannel
import java.nio.file.Path
import java.nio.file.StandardOpenOption
import java.util.concurrent.Future
import kotlin.math.min

class AsynchronousFileInputStream(path: Path) : InputStream() {

	private var future: Future<Int>
	private var fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ)
	private var readBuffer = ByteBuffer.allocate(BUFFER_SIZE)
	private var writeBuffer = ByteBuffer.allocate(BUFFER_SIZE)
	private var position = 0L

	init {
		readBuffer.flip()
		writeBuffer.clear()
		future = fileChannel.read(writeBuffer, position)
	}

	override fun available(): Int {
		return (fileChannel.size() - position + readBuffer.remaining()).toInt()
	}

	override fun readNBytes(len: Int): ByteArray {
		val result = ByteArray(min(available(), len))
		read(result)
		return result
	}

	@Synchronized
	override fun read(result: ByteArray, off: Int, len: Int): Int {
		if(len == 0)
			return 0
		val position = position
		var size = 0
		for(i in 0 ..< len / BUFFER_SIZE) {
			val s = read0(result, off + i * BUFFER_SIZE, BUFFER_SIZE, position + i * BUFFER_SIZE)
			if(s != -1)
				size += s
		}
		val i = (len / BUFFER_SIZE)
		val s = read0(result, off + i * BUFFER_SIZE, len % BUFFER_SIZE, position + i * BUFFER_SIZE)
		if(s != -1)
			size += s
		return size
	}

	private fun read0(result: ByteArray, off: Int, len: Int, position: Long): Int {
		if(len == 0)
			return 0
		if(readBuffer.remaining() == 0) {
			if(this.position >= fileChannel.size())
				return -1
			updateBuffers()
			return readFromBuffers(result, off, len)
		}
		require(len >= readBuffer.remaining()) { "$len >= ${readBuffer.remaining()}" }
		val size = readFromBuffers(result, off, readBuffer.remaining())
		val s = read0(result, off + size, len - size, position + size)
		if(s != -1)
			return s + size
		return size
	}

	@Synchronized
	override fun read(): Int {
		if(readBuffer.remaining() == 0) {
			if(position >= fileChannel.size())
				return -1
			updateBuffers()
		}
		return readFromBuffers()
	}

	private fun readFromBuffers(result: ByteArray, off: Int, len: Int): Int {
		readBuffer.get(result, off, len)
		return len
	}

	private fun readFromBuffers() = readBuffer.get().toInt() and 0xff

	private fun updateBuffers() {
		val size = future.get()
		swapBuffers()
		readBuffer.flip()
		writeBuffer.clear()
		position += size
		future = fileChannel.read(writeBuffer, position)
	}

	private fun swapBuffers() {
		val t = readBuffer
		readBuffer = writeBuffer
		writeBuffer = t
	}

	override fun close() {
		super.close()
		fileChannel.close()
	}

	companion object {

		private val BUFFER_SIZE = 16384 //getUnsafeInstance().pageSize()
	}
}