package com.greentree.commons.data.channel

import java.nio.ByteBuffer
import java.nio.channels.AsynchronousChannel

interface AsyncByteChannel : AsynchronousChannel {

	val size: Long

	suspend fun read(
		dst: ByteBuffer,
		position: Long,
	): Int
}