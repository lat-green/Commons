package com.greentree.commons.serialization.descriptor

import com.greentree.commons.serialization.Decoder
import com.greentree.commons.serialization.Encoder

interface SerialDescriptor<T> {

	val serialName: String
	val elementsCount: Int

	fun encode(encoder: Encoder, value: T)
	fun decode(decoder: Decoder): T

	fun getElementIndex(name: String): Int
	fun getElementName(index: Int): String

	fun isElementOptional(index: Int): Boolean

	fun getElementDescriptor(index: Int): SerialDescriptor<*>

	companion object {

		fun builder(serialName: String) = Builder(serialName)
	}

	class Builder(
		val serialName: String,
	) {

		private val fields = mutableListOf<FieldSerialDescriptor<*>>()

		fun element(name: String, descriptor: SerialDescriptor<*>, isOptional: Boolean = false): Builder {
			fields.add(FieldSerialDescriptor(name, descriptor, isOptional))
			return this
		}

		data class FieldSerialDescriptor<T>(
			val name: String,
			val descriptor: SerialDescriptor<T>,
			val isOptional: Boolean,
		)

		fun <T> build(): SerialDescriptor<T> {
			return SimpleSerialDescriptor(serialName, fields)
		}

		class SimpleSerialDescriptor<T>(
			override val serialName: String,
			private val fields: List<FieldSerialDescriptor<*>>,
		) : SerialDescriptor<T> {

			override val elementsCount: Int
				get() = fields.size

			override fun encode(encoder: Encoder, value: T) {
				TODO("Not yet implemented")
			}

			override fun decode(decoder: Decoder): T {
				TODO("Not yet implemented")
			}

			override fun getElementIndex(name: String) = fields.map { it.name }.indexOf(name)

			override fun getElementName(index: Int) = fields[index].name

			override fun isElementOptional(index: Int) = fields[index].isOptional

			override fun getElementDescriptor(index: Int) = fields[index].descriptor
		}
	}
}
