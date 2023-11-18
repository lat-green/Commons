package com.greentree.commons.data.async

interface AsyncOutputStream : AsyncCloseable {

	suspend fun write(b: Int)

	suspend fun write(b: ByteArray, off: Int, len: Int) {
		for(i in 0 ..< len)
			write(b[off + i].toInt())
	}

	suspend fun flush() {
	}
}

suspend fun AsyncOutputStream.write(b: ByteArray) = write(b, 0, b.size)