package com.greentree.commons.serialization.format

import com.greentree.commons.serialization.serializator.SerializationStrategy
import com.greentree.commons.serialization.serializator.serialize
import java.util.*
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

interface Encoder : AutoCloseable {

	fun encodeBits(bits: BitSet) {
		val byteArray = bits.toByteArray()
		beginCollection().use { collection ->
			for((collectionIndex, byte) in byteArray.withIndex()) {
				collection.field(collectionIndex).use {
					it.encodeByte(byte)
				}
			}
		}
	}

	fun encodeBoolean(value: Boolean)

	fun encodeByte(value: Byte)
	fun encodeChar(value: Char)
	fun encodeShort(value: Short)
	fun encodeInt(value: Int)
	fun encodeLong(value: Long)

	fun encodeFloat(value: Float)
	fun encodeDouble(value: Double)

	fun encodeString(value: String)

	fun encodeClass(cls: Class<*>) = encodeString(cls.name)

	fun beginStructure(): StructureFieldGroup<Encoder>
	fun beginCollection(): CollectionFieldGroup<Encoder>

	fun encodeIntArray(value: IntArray) {
		beginSizedCollection(value.size) { c ->
			for((index, element) in value.withIndex()) {
				c.field(index).use { f ->
					f.encodeInt(value[index])
				}
			}
		}
	}

	fun <T : Any> encodeArray(serializator: SerializationStrategy<T>, value: Array<T>) {
		beginSizedCollection(value.size) { c ->
			for((index, element) in value.withIndex()) {
				c.field(index).use { f ->
					serializator.serialize(f, element)
				}
			}
		}
	}

	override fun close() {
	}
}

@OptIn(ExperimentalContracts::class)
inline fun <R> Encoder.beginSizedCollection(size: Int, block: (CollectionFieldGroup<Encoder>) -> R): R {
	contract {
		callsInPlace(block, InvocationKind.EXACTLY_ONCE)
	}
	if(this is NamedEncoder)
		return beginSizedCollection(block)
	beginStructure().use { s ->
		s.field("size").use { f ->
			f.encodeInt(size)
		}
		s.field("value").use { f ->
			f.beginCollection().use { c ->
				return block(c)
			}
		}
	}
}