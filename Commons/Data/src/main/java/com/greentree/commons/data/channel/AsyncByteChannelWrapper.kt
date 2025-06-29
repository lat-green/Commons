package com.greentree.commons.data.channel

import java.nio.ByteBuffer
import java.nio.channels.ReadableByteChannel

data class AsyncByteChannelWrapper(
	val origin: ReadableByteChannel,
) : AsyncByteChannel {

	private var isClose = false
	override val size: Long
		get() = TODO("Not yet implemented")

	override suspend fun read(dst: ByteBuffer, position: Long): Int {
		require(position == 0L)
		val size = origin.read(dst)
		return size
	}

	override fun close() {
		isClose = true
		origin.close()
	}

	override fun isOpen() = !isClose
}