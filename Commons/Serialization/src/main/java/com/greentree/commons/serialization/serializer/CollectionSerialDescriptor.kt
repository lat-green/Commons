package com.greentree.commons.serialization.serializer

import com.greentree.commons.serialization.descriptor.SerialDescriptor

class CollectionSerialDescriptor(
	private val collection: Collection<Any>,
	private val elementDescriptor: SerialDescriptor
) : SerialDescriptor {

	override val elementsCount: Int
		get() = collection.size
	override val serialName: String
		get() = collection.javaClass.name

	override fun getElementIndex(name: String) = name.toInt()

	override fun getElementName(index: Int) = index.toString()

	override fun isElementOptional(index: Int) = false

	override fun getElementDescriptor(index: Int) = elementDescriptor
}
