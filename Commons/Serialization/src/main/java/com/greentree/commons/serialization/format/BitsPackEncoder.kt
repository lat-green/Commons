package com.greentree.commons.serialization.format

import java.util.*

data class BitsPackEncoder(
	val origin: Encoder
) : Encoder {

	private val bits = BitSet()
	private var index = 0

	private fun flush() {
		if(index == 0)
			return
		val byteArray = bits.toByteArray()
		origin.beginCollection().use { collection ->
			var collectionIndex = 0
			for(byte in byteArray) {
				collection.field(collectionIndex++).use {
					it.encodeByte(byte)
				}
			}
			repeat((index - byteArray.size * Byte.SIZE_BITS + 7) / 8) {
				collection.field(collectionIndex++).use {
					it.encodeByte(0)
				}
			}
		}
		bits.clear()
		index = 0
	}

	override fun encodeBits(bits: BitSet) {
		for(index in 0 .. bits.length()) {
			val value = bits.get(index)
			encodeBoolean(value)
		}
	}

	override fun encodeBoolean(value: Boolean) {
		bits.set(index++, value)
	}

	override fun encodeByte(value: Byte) {
		flush()
		origin.encodeByte(value)
	}

	override fun encodeChar(value: Char) {
		flush()
		origin.encodeChar(value)
	}

	override fun encodeShort(value: Short) {
		flush()
		origin.encodeShort(value)
	}

	override fun encodeInt(value: Int) {
		flush()
		origin.encodeInt(value)
	}

	override fun encodeLong(value: Long) {
		flush()
		origin.encodeLong(value)
	}

	override fun encodeFloat(value: Float) {
		flush()
		origin.encodeFloat(value)
	}

	override fun encodeDouble(value: Double) {
		flush()
		origin.encodeDouble(value)
	}

	override fun encodeString(value: String) {
		flush()
		origin.encodeString(value)
	}

	override fun encodeClass(cls: Class<*>) {
		flush()
		origin.encodeClass(cls)
	}

	override fun close() {
		flush()
		origin.close()
	}

	override fun beginStructure() = BitsPackEncoderStructure(origin.beginStructure())

	override fun beginCollection() = BitsPackEncoderStructure(origin.beginCollection())
}