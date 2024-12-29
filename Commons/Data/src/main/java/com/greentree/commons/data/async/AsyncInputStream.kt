package com.greentree.commons.data.async

import java.io.EOFException
import java.io.IOException
import java.util.*
import kotlin.math.min

private const val MAX_SKIP_BUFFER_SIZE = 1024

interface AsyncInputStream : AsyncCloseable {

	suspend fun read(): Int

	suspend fun read(b: ByteArray, off: Int, len: Int): Int {
		Objects.checkFromIndexSize(off, len, b.size)
		if(len == 0) {
			return 0
		}
		var c = read()
		if(c == -1) {
			return -1
		}
		b[off] = c.toByte()
		var i = 1
		try {
			while(i < len) {
				c = read()
				if(c == -1) {
					break
				}
				b[off + i] = c.toByte()
				i++
			}
		} catch(_: IOException) {
		}
		return i
	}

	suspend fun skip(n: Long): Long {
		var remaining = n
		var nr: Int
		if(n <= 0) {
			return 0
		}
		val size = min(MAX_SKIP_BUFFER_SIZE.toDouble(), remaining.toDouble()).toInt()
		val skipBuffer = ByteArray(size)
		while(remaining > 0) {
			nr = read(skipBuffer, 0, min(size.toDouble(), remaining.toDouble()).toInt())
			if(nr < 0) {
				break
			}
			remaining -= nr.toLong()
		}
		return n - remaining
	}

	suspend fun skipNBytes(n: Long) {
		var n = n
		while(n > 0) {
			val ns = skip(n)
			if(ns in 1 .. n) {
				n -= ns
			} else if(ns == 0L) {
				if(read() == -1) {
					throw EOFException()
				}
				n--
			} else {
				throw IOException("Unable to skip exactly")
			}
		}
	}

	suspend fun available(): Int
}

suspend fun AsyncInputStream.read(b: ByteArray) = read(b, 0, b.size)