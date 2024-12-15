package com.greentree.commons.data.stream

import java.io.InputStream
import java.nio.ByteBuffer

data class ByteBufferInputStream(
	val bytes: ByteBuffer,
) : InputStream() {

	override fun read() = if(bytes.hasRemaining())
		bytes.get().toInt()
	else
		-1
}