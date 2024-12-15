package com.greentree.commons.data.channel

import java.nio.ByteBuffer
import java.nio.channels.AsynchronousChannel
import java.util.concurrent.Future
import kotlin.coroutines.Continuation
import kotlin.coroutines.suspendCoroutine

interface AsyncByteChannel : AsynchronousChannel {

	val size: Long

	fun read(
		dst: ByteBuffer,
		position: Long,
	): Future<Int>

	fun read(
		dst: ByteBuffer,
		position: Long,
		continuation: Continuation<Int>,
	)
}

suspend fun AsyncByteChannel.suspendRead(
	dst: ByteBuffer,
	position: Long = 0,
) = suspendCoroutine { continuation ->
	read(dst, position, continuation)
}