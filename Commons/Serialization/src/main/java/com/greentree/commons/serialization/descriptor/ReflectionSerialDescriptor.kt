package com.greentree.commons.serialization.descriptor

import com.greentree.commons.serialization.CompositeDecoder
import com.greentree.commons.serialization.CompositeEncoder
import com.greentree.commons.serialization.serializer.serializer
import java.lang.reflect.Modifier

data class ReflectionSerialDescriptor<T : Any>(
	val cls: Class<T>,
) : SerialDescriptor<T> {

	override val serialName: String
		get() = cls.name
	override val elementsCount: Int
		get() = fields.size

	override fun encode(encoder: CompositeEncoder, value: T) =
		encoder.encodeSerializableElement(serializer(cls), value)

	override fun decode(decoder: CompositeDecoder) =
		decoder.decodeSerializableElement(serializer(cls))

	private val fields
		get() = cls.declaredFields.filter { !Modifier.isStatic(it.modifiers) }
			.filter { !Modifier.isTransient(it.modifiers) }

	override fun getElementIndex(name: String): Int {
		TODO("Not yet implemented")
	}

	override fun getElementName(index: Int) = fields[index].name

	override fun isElementOptional(index: Int) = false

	override fun getElementDescriptor(index: Int) = fields[index].type.descriptor
}