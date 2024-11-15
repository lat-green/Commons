package com.greentree.commons.data.resource

import com.greentree.commons.action.observable.RunObservable
import com.greentree.commons.data.asScattering
import java.io.InputStream
import java.io.Reader
import java.nio.channels.Channels
import java.nio.channels.ReadableByteChannel
import java.nio.channels.ScatteringByteChannel
import java.nio.charset.Charset

interface FileResource : ChildResource {

	val extension: String
		get() = name.substringAfterLast('.')

	/**
	 * @return length or -1 if unknown
	 */
	val length: Long

	fun open(): InputStream

	fun openChannel(): ScatteringByteChannel = Channels.newChannel(open()).asScattering

	val onCreate: RunObservable
		get() = TODO("Not yet implemented")
	val onModify: RunObservable
		get() = TODO("Not yet implemented")
	val onDelete: RunObservable
		get() = TODO("Not yet implemented")
}

fun FileResource.onModify(listener: Runnable) = onModify.addListener(listener)

fun FileResource.readBytes() = open().use { it.readBytes() }

fun FileResource.reader(charset: Charset = Charsets.UTF_8) = open().reader(charset)
fun FileResource.readText(charset: Charset = Charsets.UTF_8) = reader(charset).use { it.readText() }

fun ReadableByteChannel.reader(charset: Charset = Charsets.UTF_8): Reader = Channels.newReader(this, charset)
fun ReadableByteChannel.readText(charset: Charset = Charsets.UTF_8) = reader(charset).use { it.readText() }