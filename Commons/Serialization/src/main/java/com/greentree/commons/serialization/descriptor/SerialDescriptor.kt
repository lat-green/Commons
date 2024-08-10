package com.greentree.commons.serialization.descriptor

interface SerialDescriptor {

	val serialName: String
	val elementsCount: Int

	fun getElementIndex(name: String): Int
	fun getElementName(index: Int): String

	fun isElementOptional(index: Int): Boolean

	fun getElementDescriptor(index: Int): SerialDescriptor

	companion object {

		fun builder(serialName: String) = Builder(serialName)
	}

	class Builder(
		val serialName: String,
	) {

		private val fields = mutableListOf<FieldSerialDescriptor>()

		fun element(name: String, descriptor: SerialDescriptor, isOptional: Boolean = false): Builder {
			fields.add(FieldSerialDescriptor(name, descriptor, isOptional))
			return this
		}

		data class FieldSerialDescriptor(
			val name: String,
			val descriptor: SerialDescriptor,
			val isOptional: Boolean,
		)

		fun build(): SerialDescriptor {
			return SimpleSerialDescriptor(serialName, fields)
		}

		class SimpleSerialDescriptor(
			override val serialName: String,
			private val fields: List<FieldSerialDescriptor>,
		) : SerialDescriptor {

			override val elementsCount: Int
				get() = fields.size

			override fun getElementIndex(name: String) = fields.map { it.name }.indexOf(name)

			override fun getElementName(index: Int) = fields[index].name

			override fun isElementOptional(index: Int) = fields[index].isOptional

			override fun getElementDescriptor(index: Int) = fields[index].descriptor
		}
	}
}
