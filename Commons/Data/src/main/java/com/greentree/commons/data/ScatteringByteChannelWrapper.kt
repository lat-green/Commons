package com.greentree.commons.data

import java.nio.ByteBuffer
import java.nio.channels.ReadableByteChannel
import java.nio.channels.ScatteringByteChannel

data class ScatteringByteChannelWrapper(
	val origin: ReadableByteChannel,
) : ScatteringByteChannel, ReadableByteChannel by origin {

	override fun read(dsts: Array<out ByteBuffer>, offset: Int, length: Int): Long {
		var len = 0L
		for(index in 0 ..< length) {
			val buf = dsts[index + offset]
			val l = read(buf)
			if(l == -1)
				return -1
			len += l
		}
		return len
	}

	override fun read(dsts: Array<out ByteBuffer>): Long = read(dsts, 0, dsts.size)
}

val ReadableByteChannel.asScattering: ScatteringByteChannel
	get() = if(this is ScatteringByteChannel)
		this
	else
		ScatteringByteChannelWrapper(this)