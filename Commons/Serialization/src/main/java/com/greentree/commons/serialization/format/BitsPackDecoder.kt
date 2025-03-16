package com.greentree.commons.serialization.format

import java.util.*

data class BitsPackDecoder(
	val origin: Decoder
) : Decoder {

	private var collection: Structure<Decoder>? = null
	private var collectionIndex = 0
	private val bits = BitSet()
	private var index = 8

	private fun flush() {
		val collection = collection
		if(collection != null) {
			collection.close()
			this.collection = null
		}
		collectionIndex = 0
		bits.clear()
		index = 8
	}

	override fun decodeBits(countBits: Int): BitSet {
		val result = BitSet()
		repeat(countBits) { index ->
			result.set(index, decodeBoolean())
		}
		return result
	}

	override fun decodeBoolean(): Boolean {
		var collection = collection
		if(collection == null) {
			collection = origin.beginCollection()
			this.collection = collection
		}
		if(index > 7) {
			val b = collection.field(collectionIndex++).use {
				it.decodeByte()
			}
			index = 0
			bits.clear()
			bits.xor(BitSet.valueOf(byteArrayOf(b)))
		}
		return bits.get(index++)
	}

	override fun decodeByte(): Byte {
		flush()
		return origin.decodeByte()
	}

	override fun decodeChar(): Char {
		flush()
		return origin.decodeChar()
	}

	override fun decodeShort(): Short {
		flush()
		return origin.decodeShort()
	}

	override fun decodeInt(): Int {
		flush()
		return origin.decodeInt()
	}

	override fun decodeLong(): Long {
		flush()
		return origin.decodeLong()
	}

	override fun decodeFloat(): Float {
		flush()
		return origin.decodeFloat()
	}

	override fun decodeDouble(): Double {
		flush()
		return origin.decodeDouble()
	}

	override fun decodeString(): String {
		flush()
		return origin.decodeString()
	}

	override fun decodeClass(): Class<*> {
		flush()
		return origin.decodeClass()
	}

	override fun close() {
		flush()
		origin.close()
	}

	override fun beginStructure() = BitsPackDecoderStructure(origin.beginStructure())

	override fun beginCollection() = BitsPackDecoderStructure(origin.beginCollection())
}