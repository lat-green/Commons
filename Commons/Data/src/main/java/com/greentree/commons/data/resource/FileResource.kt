package com.greentree.commons.data.resource

import com.greentree.commons.action.observable.RunObservable
import com.greentree.commons.data.asScattering
import com.greentree.commons.data.channel.AsyncByteChannel
import com.greentree.commons.data.channel.AsyncByteChannelWrapper
import com.greentree.commons.data.stream.ByteBufferInputStream
import java.io.InputStream
import java.io.Reader
import java.nio.ByteBuffer
import java.nio.channels.Channels
import java.nio.channels.ReadableByteChannel
import java.nio.channels.ScatteringByteChannel
import java.nio.charset.Charset
import kotlin.coroutines.suspendCoroutine

interface FileResource : ChildResource {

	val extension: String
		get() = name.substringAfterLast('.')

	/**
	 * @return length or -1 if unknown
	 */
	val length: Long

	fun open(): InputStream

	fun openChannel(): ScatteringByteChannel = Channels.newChannel(open()).asScattering
	fun openAsyncChannel(): AsyncByteChannel = AsyncByteChannelWrapper(openChannel())

	val onCreate: RunObservable
		get() = TODO("Not yet implemented")
	val onModify: RunObservable
		get() = TODO("Not yet implemented")
	val onDelete: RunObservable
		get() = TODO("Not yet implemented")
}

fun FileResource.onCreate(listener: Runnable) = onCreate.addListener(listener)
fun FileResource.onModify(listener: Runnable) = onModify.addListener(listener)
fun FileResource.onDelete(listener: Runnable) = onDelete.addListener(listener)

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
		suspendCoroutine {
			byteChannel.read(bytes, 0, it)
		}
		bytes.rewind()
		return ByteBufferInputStream(bytes).reader(charset).readText()
	}
}
