package com.greentree.commons.data.channel

import com.greentree.commons.util.concurent.DoneFuture
import java.nio.ByteBuffer
import java.nio.channels.ReadableByteChannel
import java.util.concurrent.Future
import kotlin.coroutines.Continuation

data class AsyncByteChannelWrapper(
	val origin: ReadableByteChannel,
) : AsyncByteChannel {

	private var isClose = false
	override val size: Long
		get() = TODO("Not yet implemented")

	override fun read(dst: ByteBuffer, position: Long): Future<Int> {
		require(position == 0L)
		val size = origin.read(dst)
		return DoneFuture(size)
	}

	override fun read(dst: ByteBuffer, position: Long, block: Continuation<Int>) {
		TODO("Not yet implemented")
	}

	override fun close() {
		isClose = true
		origin.close()
	}

	override fun isOpen() = !isClose
}