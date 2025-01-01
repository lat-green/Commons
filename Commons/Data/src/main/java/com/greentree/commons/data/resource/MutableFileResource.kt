package com.greentree.commons.data.resource

import com.greentree.commons.data.asGathering
import java.io.OutputStream
import java.nio.ByteBuffer
import java.nio.channels.Channels
import java.nio.channels.GatheringByteChannel
import java.nio.channels.WritableByteChannel
import java.nio.charset.Charset

interface MutableFileResource : FileResource, MutableResource, MutableChildResource {

	fun setLastModified(time: Long)

	fun createThisFile(): Boolean

	fun openWrite(): OutputStream
	fun openWriteChannel(): GatheringByteChannel = Channels.newChannel(openWrite()).asGathering
}

fun WritableByteChannel.writeText(text: String, charset: Charset = Charsets.UTF_8) =
	writeBytes(text.toByteArray(charset))

fun WritableByteChannel.writeBytes(array: ByteArray) =
	write(ByteBuffer.wrap(array))

fun MutableFileResource.writeText(text: String, charset: Charset = Charsets.UTF_8) =
	writeBytes(text.toByteArray(charset))

fun MutableFileResource.writeBytes(array: ByteArray) = openWrite().use { it.write(array) }

fun FileResource.writeTo(result: MutableFileResource, lastRead: Long) {
	val m = lastModified()
	if(m == 0L || m > lastRead)
		writeTo(result)
}

fun FileResource.writeTo(result: MutableFileResource) {
	result.openWrite().use { out -> open().use { `in` -> `in`.transferTo(out) } }
}