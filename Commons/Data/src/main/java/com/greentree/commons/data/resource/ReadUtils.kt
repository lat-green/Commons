package com.greentree.commons.data.resource

import com.greentree.commons.data.stream.ByteBufferInputStream
import java.io.Reader
import java.nio.ByteBuffer
import java.nio.channels.Channels
import java.nio.channels.ReadableByteChannel
import java.nio.charset.Charset

fun FileResource.readBytes() = open().use { it.readBytes() }

fun FileResource.reader(charset: Charset = Charsets.UTF_8) = open().reader(charset)
fun FileResource.bufferedReader(charset: Charset = Charsets.UTF_8) = open().bufferedReader(charset)
fun FileResource.readText(charset: Charset = Charsets.UTF_8) = bufferedReader(charset).use { it.readText() }

fun ReadableByteChannel.reader(charset: Charset = Charsets.UTF_8): Reader = Channels.newReader(this, charset)
fun ReadableByteChannel.readText(charset: Charset = Charsets.UTF_8) = reader(charset).use { it.readText() }

suspend fun FileResource.readTextAsync(charset: Charset = Charsets.UTF_8): String {
	openAsyncChannel().use { byteChannel ->
		val size = if(length == -1L) byteChannel.size else length
		val bytes = ByteBuffer.allocate(size.toInt())
		byteChannel.read(bytes, 0)
		bytes.rewind()
		return ByteBufferInputStream(bytes).reader(charset).use {
			it.readText()
		}
	}
}

suspend fun FileResource.readBytesAsync(): ByteArray {
	openAsyncChannel().use { byteChannel ->
		val size = (if(length == -1L) byteChannel.size else length).toInt()
		val result = ByteArray(size)
		val bytes = ByteBuffer.wrap(result)
		byteChannel.read(bytes, 0)
		return result
	}
}
