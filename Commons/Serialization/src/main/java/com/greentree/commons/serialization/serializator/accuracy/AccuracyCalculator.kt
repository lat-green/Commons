package com.greentree.commons.serialization.serializator.accuracy

import com.greentree.commons.serialization.format.Decoder
import com.greentree.commons.serialization.format.Encoder

interface AccuracyCalculator<T> {

	val countValues: Long
	val sizeBits
		get() = fold2(countValues)
	val sizeBytes
		get() = (sizeBits + 7.toByte()) / 8.toByte()

	fun encodeValue(value: T): Long
	fun decodeValue(value: Long): T
}

fun <T> AccuracyCalculator<T>.encode(encoder: Encoder, value: T) {
	val encodedValue = encodeValue(value)
	return when(sizeBytes) {
		0 -> {}
		1 -> encoder.encodeByte(encodedValue.toByte())
		2 -> encoder.encodeShort(encodedValue.toShort())
		3, 4 -> encoder.encodeInt(encodedValue.toInt())
		5, 6, 7, 8 -> encoder.encodeLong(encodedValue)
		else -> TODO("$sizeBytes")
	}
}

fun <T> AccuracyCalculator<T>.decode(decoder: Decoder): T {
	val encodedValue = when(sizeBytes) {
		0 -> 0L
		1 -> decoder.decodeByte().toUByte().toLong()
		2 -> decoder.decodeShort().toUShort().toLong()
		3, 4 -> decoder.decodeInt().toUInt().toLong()
		5, 6, 7, 8 -> decoder.decodeLong()
		else -> TODO("$sizeBytes")
	}
	return decodeValue(encodedValue)
}

fun fold2(a: Long): Byte {
	var result = 0.toByte()
	var a = a
	while(a > 1) {
		result++
		a /= 2
	}
	return result
}