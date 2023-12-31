package com.greentree.commons.serialization.serializer

import com.greentree.commons.serialization.data.Decoder
import com.greentree.commons.util.UnsafeUtil
import sun.misc.Unsafe

interface DeserializationStrategy<T : Any> {

	fun deserialize(decoder: Decoder): T

	fun deserializeTo(decoder: Decoder, value: T): T {
		val result = deserialize(decoder)
		require(result::class == value::class)
		val unsafe = UnsafeUtil.getUnsafeInstance()
		unsafe.copy(result, value)
		return value
	}
}

private fun <T : Any> Unsafe.copy(value: T, dest: T) {
	for(field in value::class.java.declaredFields) {
		val offset = objectFieldOffset(field)
		when(field.type) {
			Byte::class.java -> {
				val v = getByte(value, offset)
				putByte(dest, offset, v)
			}

			Short::class.java -> {
				val v = getShort(value, offset)
				putShort(dest, offset, v)
			}

			Int::class.java -> {
				val v = getInt(value, offset)
				putInt(dest, offset, v)
			}

			Long::class.java -> {
				val v = getLong(value, offset)
				putLong(dest, offset, v)
			}

			Float::class.java -> {
				val v = getFloat(value, offset)
				putFloat(dest, offset, v)
			}

			Double::class.java -> {
				val v = getDouble(value, offset)
				putDouble(dest, offset, v)
			}

			String::class.java -> {
				val v = getObject(value, offset)
				putObject(dest, offset, v)
			}

			else -> {
				val d = getObject(dest, offset)
				if(d == null) {
					putObject(value, offset, null)
				} else {
					var v = getObject(value, offset)
					if(v == null) {
						v = allocateInstance(field.type)
						putObject(value, offset, v)
					}
					copy(v, d)
				}
			}
		}
	}
}
