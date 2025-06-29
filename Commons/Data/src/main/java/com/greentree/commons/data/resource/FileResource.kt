package com.greentree.commons.data.resource

import com.greentree.commons.action.observable.RunObservable
import com.greentree.commons.data.asScattering
import com.greentree.commons.data.channel.AsyncByteChannel
import com.greentree.commons.data.channel.AsyncByteChannelWrapper
import java.io.InputStream
import java.nio.channels.Channels
import java.nio.channels.ScatteringByteChannel

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
