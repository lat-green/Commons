package com.greentree.commons.graphics.smart.mesh

import java.nio.ByteBuffer
import java.nio.FloatBuffer

interface VideoBuffer {

	val elementSize: Int
	val elementCount: Int

	fun setData(buffer: ByteBuffer)
	fun getData(buffer: ByteBuffer)

	interface Builder {

		fun buildFloat(): FloatVideoBuffer
	}
}

interface FloatVideoBuffer : VideoBuffer {

	override fun setData(buffer: ByteBuffer) = setData(buffer.asFloatBuffer())
	override fun getData(buffer: ByteBuffer) = getData(buffer.asFloatBuffer())

	fun setData(buffer: FloatBuffer)
	fun getData(buffer: FloatBuffer)
}