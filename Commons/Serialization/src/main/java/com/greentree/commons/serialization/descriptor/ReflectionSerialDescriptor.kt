package com.greentree.commons.serialization.descriptor

import java.lang.reflect.Modifier

data class ReflectionSerialDescriptor(
	val cls: Class<*>,
) : SerialDescriptor {

	override val serialName: String
		get() = cls.name
	override val elementsCount: Int
		get() = fields.size
	private val fields
		get() = cls.declaredFields
			.filter { !Modifier.isStatic(it.modifiers) }
			.filter { !Modifier.isTransient(it.modifiers) }
			.sortedBy { it.name }

	override fun getElementIndex(name: String) = fields.map { it.name }.indexOf(name)

	override fun getElementName(index: Int): String = fields[index].name

	override fun isElementOptional(index: Int) = false

	override fun getElementDescriptor(index: Int) = fields[index].type.descriptor
}