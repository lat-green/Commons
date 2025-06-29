package com.greentree.commons.data.stream

import java.io.InputStream
import java.nio.ByteBuffer

data class ByteBufferInputStream(
	val bytes: ByteBuffer,
) : InputStream() {

	override fun read(b: ByteArray, off: Int, len: Int): Int {
		val count = Math.min(len, bytes.remaining())
		bytes.get(b, off, count)
		if(count == 0)
			return -1
		return count
	}

	override fun read() = if(bytes.hasRemaining())
		bytes.get().toInt()
	else
		-1
}