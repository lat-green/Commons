package com.greentree.commons.serialization.format

import java.io.DataInput
import java.io.DataInputStream
import java.io.DataOutput
import java.io.DataOutputStream
import java.io.InputStream
import java.io.OutputStream

object Bytes {

	fun encoder(output: DataOutput) = BitsPackEncoder(ByteEncoder(output))

	fun encoder(output: OutputStream) = encoder(DataOutputStream(output))
	fun encoder(output: DataOutputStream) = encoder(output as DataOutput)

	fun decoder(input: DataInput) = BitsPackDecoder(ByteDecoder(input))

	fun decoder(input: InputStream) = decoder(DataInputStream(input))
	fun decoder(input: DataInputStream) = decoder(input as DataInput)
}

data class ByteDecoder(
	val input: DataInput,
) : Decoder, Structure<Decoder> {

	override fun beginStructure() = this
	override fun beginCollection() = this

	override fun field(name: String) = this
	override fun field(index: Int) = this

	override fun decodeBoolean(): Boolean = input.readBoolean()

	override fun decodeByte(): Byte = input.readByte()

	override fun decodeChar(): Char = input.readChar()

	override fun decodeShort(): Short = input.readShort()

	override fun decodeInt() = input.readInt()

	override fun decodeLong(): Long = input.readLong()

	override fun decodeFloat(): Float = input.readFloat()

	override fun decodeDouble(): Double = input.readDouble()

	override fun decodeString(): String = input.readUTF()

	override fun decodeClass(): Class<*> = ClassSerializeUtil.decodeClass(this)
}

data class ByteEncoder(
	val output: DataOutput,
) : Encoder, Structure<Encoder> {

	override fun beginStructure() = this
	override fun beginCollection() = this

	override fun field(name: String) = this
	override fun field(index: Int) = this

	override fun encodeBoolean(value: Boolean) = output.writeBoolean(value)

	override fun encodeByte(value: Byte) = output.writeByte(value.toInt())

	override fun encodeChar(value: Char) = output.writeChar(value.code)

	override fun encodeShort(value: Short) = output.writeShort(value.toInt())

	override fun encodeInt(value: Int) = output.writeInt(value)

	override fun encodeLong(value: Long) = output.writeLong(value)

	override fun encodeFloat(value: Float) = output.writeFloat(value)

	override fun encodeDouble(value: Double) = output.writeDouble(value)

	override fun encodeString(value: String) = output.writeUTF(value)

	override fun encodeClass(cls: Class<*>) = ClassSerializeUtil.encodeClass(this, cls)
}

