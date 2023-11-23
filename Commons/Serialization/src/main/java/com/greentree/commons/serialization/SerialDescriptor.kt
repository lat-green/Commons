package com.greentree.commons.serialization

interface SerialDescriptor {

	val serialName: String
	val elementsCount: Int

	fun encode(encoder: CompositeEncoder, value: Any)
	fun decode(decoder: CompositeDecoder): Any

	fun getElementIndex(name: String): Int
	fun getElementName(index: Int): String

	fun isElementOptional(index: Int): Boolean

	fun getElementDescriptor(index: Int): SerialDescriptor
}
