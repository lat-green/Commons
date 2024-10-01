package com.greentree.commons.data

import java.nio.ByteBuffer
import java.nio.channels.GatheringByteChannel
import java.nio.channels.WritableByteChannel

data class GatheringByteChannelWrapper(
	val origin: WritableByteChannel,
) : GatheringByteChannel, WritableByteChannel by origin {

	override fun write(srcs: Array<out ByteBuffer>, offset: Int, length: Int): Long {
		var len = 0L
		for(index in 0 ..< length) {
			val buf = srcs[index + offset]
			val l = write(buf)
			if(l == -1)
				break
			len += l
		}
		return len
	}

	override fun write(srcs: Array<out ByteBuffer>): Long = write(srcs, 0, srcs.size)
}

val WritableByteChannel.asGathering: GatheringByteChannel
	get() = if(this is GatheringByteChannel)
		this
	else
		GatheringByteChannelWrapper(this)