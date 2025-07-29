package com.greentree.commons.data.channel

import kotlinx.coroutines.future.await
import java.nio.ByteBuffer
import java.nio.channels.AsynchronousChannel
import java.nio.channels.AsynchronousFileChannel
import java.nio.channels.CompletionHandler
import java.util.concurrent.CompletableFuture

data class AsynchronousFileChannelAsyncByteChannel(
	val origin: AsynchronousFileChannel,
) : AsyncByteChannel, AsynchronousChannel by origin {

	override val size: Long
		get() = origin.size()

	override suspend fun read(dst: ByteBuffer, position: Long): Int {
		val promise = CompletableFuture<Int>()
		origin.read(dst, position, null, object : CompletionHandler<Int, Nothing?> {
			override fun completed(result: Int, attachment: Nothing?) {
				promise.complete(result)
			}

			override fun failed(exc: Throwable, attachment: Nothing?) {
				promise.completeExceptionally(exc)
			}
		})
		return promise.await()
//		return suspendCoroutine<Int> { continuation ->
//			origin.read(dst, position, null, object : CompletionHandler<Int, Nothing?> {
//				override fun completed(result: Int, attachment: Nothing?) {
//					continuation.resume(result)
//				}
//
//				override fun failed(exc: Throwable, attachment: Nothing?) {
//					continuation.resumeWithException(exc)
//				}
//			})
//		}
	}
}