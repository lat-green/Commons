package com.greentree.commons.serialization

import com.greentree.commons.serialization.descriptor.SerialDescriptor
import com.greentree.commons.serialization.serializer.DeserializationStrategy
import com.greentree.commons.serialization.serializer.SerializationStrategy
import com.greentree.commons.serialization.serializer.serializer
import java.io.InputStream
import java.io.OutputStream
/*
object Bytes {

	fun encoder(output: OutputStream) = BytesEncoder(output)
	fun decoder(input: InputStream) = BytesDecoder(input)

	inline fun <reified T : Any> encodeToSteam(value: T, output: OutputStream) {
		val encoder = encoder(output)
		serializer<T>().serialize(encoder, value)
		output.flush()
	}

	inline fun <reified T : Any> decodeFromSteam(value: InputStream): T {
		val decoder = decoder(value)
		return serializer<T>().deserialize(decoder)
	}
}

class BytesDecoder(private val input: InputStream) : Decoder {

	override fun decodeBoolean(): Boolean {
		TODO("Not yet implemented")
	}

	override fun decodeByte(): Byte {
		TODO("Not yet implemented")
	}

	override fun decodeChar(): Char {
		TODO("Not yet implemented")
	}

	override fun decodeShort(): Short {
		TODO("Not yet implemented")
	}

	override fun decodeInt(): Int {
		TODO("Not yet implemented")
	}

	override fun decodeLong(): Long {
		TODO("Not yet implemented")
	}

	override fun decodeFloat(): Float {
		TODO("Not yet implemented")
	}

	override fun decodeDouble(): Double {
		TODO("Not yet implemented")
	}

	override fun decodeString(): String {
		TODO("Not yet implemented")
	}

	override fun beginStructure(descriptor: SerialDescriptor<*>) = BytesCompositeDecoder(descriptor, input)

	override fun <T> decodeSerializableValue(deserializer: DeserializationStrategy<T>): T {
		TODO("Not yet implemented")
	}

	override fun <E : Enum<E>> decodeEnum(enumDescriptor: SerialDescriptor<E>): E {
		TODO("Not yet implemented")
	}
}

class BytesCompositeDecoder(
	private val descriptor: SerialDescriptor<*>,
	private val input: InputStream,
) : CompositeDecoder {

	override fun decodeBooleanElement(): Boolean {
		TODO("Not yet implemented")
	}

	override fun decodeByteElement(): Byte {
		TODO("Not yet implemented")
	}

	override fun decodeCharElement(): Char {
		TODO("Not yet implemented")
	}

	override fun decodeShortElement(): Short {
		return input.readShort()
	}

	override fun decodeIntElement(): Int {
		return input.readInt()
	}

	override fun decodeLongElement(): Long {
		TODO("Not yet implemented")
	}

	override fun decodeDoubleElement(): Double {
		TODO("Not yet implemented")
	}

	override fun decodeStringElement(): String {
		return input.readUTF()
	}

	override fun <T> decodeSerializableElement(deserializer: DeserializationStrategy<T>): T {
		return deserializer.deserialize(BytesDecoder(input))
	}

	override fun <E : Enum<E>> decodeEnumElement(descriptor: SerialDescriptor<E>): E {
		TODO("Not yet implemented")
	}
}

class BytesEncoder(private val output: OutputStream) : Encoder {

	override fun encodeBoolean(value: Boolean) {
		TODO("Not yet implemented")
	}

	override fun encodeByte(value: Byte) {
		TODO("Not yet implemented")
	}

	override fun encodeChar(value: Char) {
		TODO("Not yet implemented")
	}

	override fun encodeShort(value: Short) {
		TODO("Not yet implemented")
	}

	override fun encodeInt(value: Int) {
		TODO("Not yet implemented")
	}

	override fun encodeLong(value: Long) {
		TODO("Not yet implemented")
	}

	override fun encodeFloat(value: Float) {
		TODO("Not yet implemented")
	}

	override fun encodeDouble(value: Double) {
		TODO("Not yet implemented")
	}

	override fun encodeString(value: String) {
		output.writeUTF(value)
	}

	override fun <T> encodeSerializable(serializer: SerializationStrategy<T>, value: T) {
		TODO("Not yet implemented")
	}

	override fun beginStructure(descriptor: SerialDescriptor<*>) = BytesCompositeEncoder(descriptor, output)
	override fun <E : Enum<E>> encodeEnumElement(descriptor: SerialDescriptor<E>, value: E) {
		TODO("Not yet implemented")
	}
}

class BytesCompositeEncoder(
	private val descriptor: SerialDescriptor<*>,
	private val output: OutputStream,
) : CompositeEncoder {

	override fun encodeBooleanElement(value: Boolean) {
		output.writeBoolean(value)
	}

	override fun encodeByteElement(value: Byte) {
		output.writeByte(value)
	}

	override fun encodeShortElement(value: Short) {
		output.writeShort(value)
	}

	override fun encodeIntElement(value: Int) {
		output.writeInt(value)
	}

	override fun encodeLongElement(value: Long) {
		output.writeLong(value)
	}

	override fun encodeStringElement(value: String) {
		output.writeUTF(value)
	}

	override fun encodeFloatElement(value: Float) {
//		output.writeFloat(value)
	}

	override fun encodeDoubleElement(value: Double) {
//		output.writeDouble(value)
	}

	override fun <T> encodeSerializableElement(serializer: SerializationStrategy<T>, value: T) {
		serializer.serialize(BytesEncoder(output), value)
	}

	override fun <E : Enum<E>> encodeEnumElement(descriptor: SerialDescriptor<E>, value: E) {
		TODO("Not yet implemented")
	}
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

private fun OutputStream.writeInt(value: Int) {
	write(value ushr 24 and 0xFF)
	write(value ushr 16 and 0xFF)
	write(value ushr 8 and 0xFF)
	write(value ushr 0 and 0xFF)
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
	while(read().also { byte = it.toByte() } != 0) {
		bytes.add(byte)
	}
	return String(bytes.toByteArray())
}

private fun InputStream.readBoolean(): Boolean {
	return read() == 1
}

private fun InputStream.readInt(): Int {
	val a = read()
	val b = read()
	val c = read()
	val d = read()
	return (a shl 24) or (b shl 16) or (c shl 8) or d
}

private fun InputStream.readLong(): Long {
	val a = read().toLong()
	val b = read().toLong()
	val c = read().toLong()
	val d = read().toLong()
	val e = read().toLong()
	val f = read().toLong()
	val g = read().toLong()
	val k = read().toLong()
	return (a shl 56) or (b shl 48) or (c shl 40) or (d shl 32) or (e shl 24) or (f shl 16) or (g shl 8) or k
}

private fun InputStream.readShort(): Short {
	val a = read()
	val b = read()
	return ((a shl 8) or b).toShort()
}

private fun InputStream.readByte(): Byte {
	val a = read()
	return a.toByte()
}
*/