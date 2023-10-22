package com.greentree.commons.graphics.smart

import java.nio.ByteBuffer
import java.nio.FloatBuffer
import java.nio.IntBuffer

interface StackBuffer : AutoCloseable {

	fun malloc(alignment: Int, size: Int): ByteBuffer

	fun floats(vararg values: Float): FloatBuffer
	fun ints(vararg values: Int): IntBuffer

	fun mallocByte(size: Int): ByteBuffer
	fun mallocFloat(size: Int): FloatBuffer
	fun mallocInt(size: Int): IntBuffer

}
