package com.greentree.commons.coroutine

import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.AsynchronousSocketChannel
import java.nio.channels.CompletionHandler
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

private fun <T, A> Continuation<T>.asCompletionHandler(): CompletionHandler<T, A> {
	return object : CompletionHandler<T, A> {
		override fun completed(result: T, attachment: A) {
			resume(result)
		}

		override fun failed(exc: Throwable, attachment: A) {
			resumeWithException(exc)
		}
	}
}

suspend fun AsynchronousSocketChannel.connectAsync(remote: InetSocketAddress): Void? = suspendCoroutine { cont ->
	connect(remote, Unit, cont.asCompletionHandler())
}

suspend fun AsynchronousSocketChannel.write(array: ByteArray): Int = suspendCoroutine { cont ->
	val buf = ByteBuffer.wrap(array)
	write(buf, Unit, cont.asCompletionHandler())
}

suspend fun AsynchronousSocketChannel.read(array: ByteArray): Int = suspendCoroutine { cont ->
	val buf = ByteBuffer.wrap(array)
	read(buf, Unit, cont.asCompletionHandler())
}