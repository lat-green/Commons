package com.greentree.commons.serialization.descriptor

import com.greentree.commons.serialization.Decoder
import com.greentree.commons.serialization.Encoder
import com.greentree.commons.serialization.serializer.serializer
import java.lang.reflect.Modifier

data class ReflectionSerialDescriptor<T : Any>(
	val cls: Class<T>,
) : SerialDescriptor<T> {

	override val serialName: String
		get() = cls.name
	override val elementsCount: Int
		get() = fields.size

	override fun encode(encoder: Encoder, value: T) =
		serializer(cls).serialize(encoder, value)

	override fun decode(decoder: Decoder) =
		serializer(cls).deserialize(decoder)

	private val fields
		get() = cls.declaredFields.filter { !Modifier.isStatic(it.modifiers) }
			.filter { !Modifier.isTransient(it.modifiers) }

	override fun getElementIndex(name: String) = fields.map { it.name }.indexOf(name)

	override fun getElementName(index: Int) = fields[index].name

	override fun isElementOptional(index: Int) = false

	override fun getElementDescriptor(index: Int) = fields[index].type.descriptor
}