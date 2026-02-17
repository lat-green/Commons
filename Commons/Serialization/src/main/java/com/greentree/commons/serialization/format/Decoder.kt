package com.greentree.commons.serialization.format

import com.greentree.commons.serialization.serializator.DeserializationStrategy
import com.greentree.commons.serialization.serializator.deserialize
import java.util.*
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

interface Decoder : AutoCloseable {

	fun decodeBits(countBits: Int): BitSet {
		beginCollection().use { collection ->
			val byteArray = ByteArray((countBits + 7) / 8) { index ->
				collection.field(index).use {
					it.decodeByte()
				}
			}
			return BitSet.valueOf(byteArray)
		}
	}

	fun decodeBoolean(): Boolean

	fun decodeByte(): Byte
	fun decodeChar(): Char
	fun decodeShort(): Short
	fun decodeInt(): Int
	fun decodeLong(): Long

	fun decodeFloat(): Float
	fun decodeDouble(): Double

	fun decodeString(): String

	fun decodeClass(): Class<*> = Class.forName(decodeString())

	fun beginStructure(): StructureFieldGroup<Decoder>
	fun beginCollection(): CollectionFieldGroup<Decoder>

	fun decodeIntArray(): IntArray {
		beginSizedCollection { size, c ->
			return IntArray(size) {
				c.field(it).use { f ->
					f.decodeInt()
				}
			}
		}
	}

	fun <T : Any> decodeArray(serializator: DeserializationStrategy<T>): Array<T> {
		val componentType = serializator.type.toClass()
		beginSizedCollection { size, c ->
			val result = java.lang.reflect.Array.newInstance(componentType, size) as Array<T>
			repeat(size) {
				result[it] =
					c.field(it).use { f ->
						serializator.deserialize(f)
					}
			}
			return result
		}
	}

	override fun close() {
	}
}

@OptIn(ExperimentalContracts::class)
inline fun <R> Decoder.beginSizedCollection(block: (size: Int, CollectionFieldGroup<Decoder>) -> R): R {
	contract {
		callsInPlace(block, InvocationKind.EXACTLY_ONCE)
	}
	if(this is NamedDecoder)
		beginCollection().use { c ->
			return block(c.size, c)
		}
	beginStructure().use { s ->
		val size = s.field("size").use { f ->
			f.decodeInt()
		}
		s.field("value").use { f ->
			f.beginCollection().use { c ->
				return block(size, c)
			}
		}
	}
}