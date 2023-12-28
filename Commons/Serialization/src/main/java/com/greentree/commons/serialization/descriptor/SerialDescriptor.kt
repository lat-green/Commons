package com.greentree.commons.serialization.descriptor

import com.greentree.commons.serialization.CompositeDecoder
import com.greentree.commons.serialization.CompositeEncoder

interface SerialDescriptor<T> {

	val serialName: String
	val elementsCount: Int

	fun encode(encoder: CompositeEncoder, value: T)
	fun decode(decoder: CompositeDecoder): T

	fun getElementIndex(name: String): Int
	fun getElementName(index: Int): String

	fun isElementOptional(index: Int): Boolean

	fun getElementDescriptor(index: Int): SerialDescriptor<*>
}
