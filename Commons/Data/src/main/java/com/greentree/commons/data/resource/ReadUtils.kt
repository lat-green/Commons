package com.greentree.commons.data.resource

import java.io.Reader
import java.nio.ByteBuffer
import java.nio.channels.Channels
import java.nio.channels.ReadableByteChannel
import java.nio.charset.Charset
import java.util.concurrent.CompletableFuture

fun FileResource.readBytes() = open().use { it.readBytes() }

fun FileResource.reader(charset: Charset = Charsets.UTF_8) = open().reader(charset)
fun FileResource.bufferedReader(charset: Charset = Charsets.UTF_8) = open().bufferedReader(charset)
fun FileResource.readText(charset: Charset = Charsets.UTF_8) = bufferedReader(charset).use { it.readText() }

fun ReadableByteChannel.reader(charset: Charset = Charsets.UTF_8): Reader = Channels.newReader(this, charset)
fun ReadableByteChannel.readText(charset: Charset = Charsets.UTF_8) = reader(charset).use { it.readText() }

suspend fun FileResource.readTextAsync(charset: Charset = Charsets.UTF_8) = String(readBytesAsync(), charset)

suspend fun FileResource.readBytesAsync(): ByteArray {
	openAsyncChannel().use { byteChannel ->
		val fullSize = (if(length == -1L) byteChannel.size else length).toInt()
		val result = ByteArray(fullSize)
		val buffer = ByteBuffer.wrap(result)
		byteChannel.read(buffer, 0)
		return result
	}
}
