package com.greentree.commons.serialization.data

import com.greentree.commons.serialization.descriptor.SerialDescriptor
import com.greentree.commons.serialization.descriptor.descriptor
import java.io.DataInputStream
import java.io.EOFException
import java.io.InputStream
import java.io.OutputStream
import kotlin.reflect.KClass

object Bytes {

	fun encoder(output: OutputStream) = BytesEncoder(output)
	fun decoder(input: InputStream) = BytesDecoder(DataInputStream(input))

	fun <T : Any> encodeToSteam(cls: KClass<T>, value: T, output: OutputStream) {
		val encoder = encoder(output)
		cls.java.descriptor.encode(encoder, value)
		output.flush()
	}

	fun <T : Any> decodeFromSteam(cls: KClass<T>, value: InputStream): T {
		val decoder = decoder(value)
		return cls.java.descriptor.decode(decoder)
	}

	fun <T : Any> encodeToSteam(cls: Class<T>, value: T, output: OutputStream) {
		val encoder = encoder(output)
		cls.descriptor.encode(encoder, value)
		output.flush()
	}

	fun <T : Any> decodeFromSteam(cls: Class<T>, value: InputStream): T {
		val decoder = decoder(value)
		return cls.descriptor.decode(decoder)
	}

	inline fun <reified T : Any> encodeToSteam(value: T, output: OutputStream) {
		val encoder = encoder(output)
		T::class.java.descriptor.encode(encoder, value)
		output.flush()
	}

	inline fun <reified T : Any> decodeFromSteam(value: InputStream): T {
		val decoder = decoder(value)
		return T::class.java.descriptor.decode(decoder)
	}
}

class BytesEncoder(private val output: OutputStream) : Encoder, Structure<Encoder> {

	override fun encodeBoolean(value: Boolean) = output.writeBoolean(value)

	override fun encodeByte(value: Byte) = output.writeByte(value)

	override fun encodeChar(value: Char) = output.writeChar(value)

	override fun encodeShort(value: Short) = output.writeShort(value)

	override fun encodeInt(value: Int) = output.writeInt(value)

	override fun encodeLong(value: Long) = output.writeLong(value)

	override fun encodeFloat(value: Float) = output.writeFloat(value)

	override fun encodeDouble(value: Double) = output.writeDouble(value)

	override fun encodeString(value: String) = output.writeUTF(value)

	override fun beginStructure(descriptor: SerialDescriptor<*>): Structure<Encoder> {
		return this
	}

	override fun field(name: String) = this

	override fun field(index: Int) = this
}

class BytesDecoder(private val input: DataInputStream) : Decoder, Structure<Decoder> {

	override fun decodeBoolean(): Boolean = input.readBoolean()

	override fun decodeByte(): Byte = input.readByte()

	override fun decodeChar(): Char = input.readChar()

	override fun decodeShort(): Short = input.readShort()

	override fun decodeInt(): Int = input.readInt()

	override fun decodeLong(): Long = input.readLong()

	override fun decodeFloat(): Float = input.readFloat()

	override fun decodeDouble(): Double = input.readDouble()

	override fun decodeString(): String = input.readUTF()

	override fun beginStructure(descriptor: SerialDescriptor<*>) = this

	override fun field(name: String) = this

	override fun field(index: Int) = this
}

private fun OutputStream.writeUTF(value: String) {
	write(value.toByteArray())
	write(0)
}

private fun OutputStream.writeBoolean(value: Boolean) {
	write(if(value) 1 else 0)
}

private fun OutputStream.writeShort(value: Short) {
	val value = value.toInt()
	write(value ushr 8 and 0xFF)
	write(value ushr 0 and 0xFF)
}

private fun OutputStream.writeByte(value: Byte) {
	val value = value.toInt()
	write(value and 0xFF)
}

private fun OutputStream.writeChar(value: Char) {
	val value = value.code
	write(value ushr 8 and 0xFF)
	write(value ushr 0 and 0xFF)
}

private fun OutputStream.writeInt(value: Int) {
	write(value ushr 24 and 0xFF)
	write(value ushr 16 and 0xFF)
	write(value ushr 8 and 0xFF)
	write(value ushr 0 and 0xFF)
}

private fun OutputStream.writeFloat(value: Float) {
	writeInt(value.toRawBits())
}

private fun OutputStream.writeDouble(value: Double) {
	writeLong(value.toRawBits())
}

private fun OutputStream.writeLong(value: Long) {
	fun write(b: Long) = write(b.toInt())
	write(value ushr 56 and 0xFF)
	write(value ushr 48 and 0xFF)
	write(value ushr 40 and 0xFF)
	write(value ushr 32 and 0xFF)
	write(value ushr 24 and 0xFF)
	write(value ushr 16 and 0xFF)
	write(value ushr 8 and 0xFF)
	write(value ushr 0 and 0xFF)
}

private fun InputStream.readUTF(): String {
	val bytes = mutableListOf<Byte>()
	var byte: Byte
	while(readOrEOF().also { byte = it.toByte() } != 0) {
		bytes.add(byte)
	}
	return String(bytes.toByteArray())
}

private fun InputStream.readBoolean(): Boolean {
	return readOrEOF() == 1
}

private fun InputStream.readInt(): Int {
	val a = readOrEOF()
	val b = readOrEOF()
	val c = readOrEOF()
	val d = readOrEOF()
	return (a shl 24) or (b shl 16) or (c shl 8) or d
}

private fun InputStream.readLong(): Long {
	val a = readOrEOF().toLong()
	val b = readOrEOF().toLong()
	val c = readOrEOF().toLong()
	val d = readOrEOF().toLong()
	val e = readOrEOF().toLong()
	val f = readOrEOF().toLong()
	val g = readOrEOF().toLong()
	val k = readOrEOF().toLong()
	return (a shl 56) or (b shl 48) or (c shl 40) or (d shl 32) or (e shl 24) or (f shl 16) or (g shl 8) or k
}

private fun InputStream.readShort(): Short {
	val a = readOrEOF()
	val b = readOrEOF()
	return ((a shl 8) or b).toShort()
}

private fun InputStream.readByte(): Byte {
	val a = readOrEOF()
	return a.toByte()
}

private fun InputStream.readOrEOF() = run {
	val b = read()
	if(b == -1)
		throw EOFException()
	b
}