package com.greentree.commons.data.channel

import java.nio.ByteBuffer
import java.nio.channels.AsynchronousChannel
import java.nio.channels.AsynchronousFileChannel
import java.nio.channels.CompletionHandler
import java.util.concurrent.Future
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

data class AsynchronousFileChannelAsyncByteChannel(
	val origin: AsynchronousFileChannel,
) : AsyncByteChannel, AsynchronousChannel by origin {

	override val size: Long
		get() = origin.size()

	override fun read(dst: ByteBuffer, position: Long): Future<Int> = origin.read(dst, position)

	override fun read(dst: ByteBuffer, position: Long, continuation: Continuation<Int>) =
		origin.read(dst, position, null, object : CompletionHandler<Int, Nothing?> {
			override fun completed(result: Int, attachment: Nothing?) {
				continuation.resume(result)
			}

			override fun failed(exc: Throwable, attachment: Nothing?) {
				continuation.resumeWithException(exc)
			}
		})
}