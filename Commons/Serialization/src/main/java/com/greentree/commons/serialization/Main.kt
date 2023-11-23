package com.greentree.commons.serialization

import java.lang.reflect.Modifier
import kotlin.jvm.internal.DefaultConstructorMarker

data class Person(val name: Name, val age: UShort)

interface Name {

	val value: String
}

data class NameImpl(override val value: String) : Name

abstract class PrimitiveSerialDescriptor(cls: Class<*>) : SerialDescriptor {

	override val serialName: String = cls.name
	override val elementsCount: Int
		get() = 0

	override fun getElementIndex(name: String): Int {
		TODO("Not yet implemented")
	}

	override fun getElementName(index: Int): String {
		TODO("Not yet implemented")
	}

	override fun isElementOptional(index: Int) = false

	override fun getElementDescriptor(index: Int): SerialDescriptor {
		TODO("Not yet implemented")
	}
}

object IntSerialDescriptor : PrimitiveSerialDescriptor(Int::class.java) {

	override fun encode(encoder: CompositeEncoder, value: Any) = encoder.encodeIntElement(value as Int)
	override fun decode(decoder: CompositeDecoder) = decoder.decodeIntElement()
}

object StringSerialDescriptor : PrimitiveSerialDescriptor(String::class.java) {

	override fun encode(encoder: CompositeEncoder, value: Any) = encoder.encodeStringElement(value as String)
	override fun decode(decoder: CompositeDecoder) = decoder.decodeStringElement()
}

object ShortSerialDescriptor : PrimitiveSerialDescriptor(Short::class.java) {

	override fun encode(encoder: CompositeEncoder, value: Any) = encoder.encodeShortElement(value as Short)
	override fun decode(decoder: CompositeDecoder) = decoder.decodeShortElement()
}

val <T> Class<T>.descriptor: SerialDescriptor
	get() = when(this) {
		String::class.java -> StringSerialDescriptor
		Int::class.java -> IntSerialDescriptor
		Short::class.java -> ShortSerialDescriptor
		else -> ReflectionSerialDescriptor(this)
	}

data class ReflectionSerialDescriptor(
	val cls: Class<*>,
) : SerialDescriptor {

	override val serialName: String
		get() = cls.name
	override val elementsCount: Int
		get() = fields.size

	override fun encode(encoder: CompositeEncoder, value: Any) =
		encoder.encodeSerializableElement(serializer(cls), value)

	override fun decode(decoder: CompositeDecoder) =
		decoder.decodeSerializableElement(serializer<Any>(cls))

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

class NotFinalClassesSerializer<T>(private val cls: Class<T>) : Serializer<T> {

	init {
		require(!Modifier.isFinal(cls.modifiers)) { "type $cls is final" }
	}

	override val descriptor: SerialDescriptor
		get() = TODO("Not yet implemented")

	override fun deserialize(decoder: Decoder): T {
		TODO("Not yet implemented")
	}

	override fun serialize(encoder: Encoder, value: T) {
		encoder.encodeString(value?.let { it::class }!!.java.simpleName)
		serializer<T>(value.let { it::class }.java).serialize(encoder, value)
	}
}

class ReflectionSerializer<T>(override val descriptor: ReflectionSerialDescriptor) : Serializer<T> {

	init {
		require(Modifier.isFinal(descriptor.cls.modifiers)) { "type ${descriptor.cls} is not final" }
	}

	override fun deserialize(decoder: Decoder): T {
		val struct = decoder.beginStructure(descriptor)
		val parameters = mutableListOf<Any>()
		for(i in 0 ..< descriptor.elementsCount) {
			val fieldTypeDescriptor = descriptor.getElementDescriptor(i)
			val value = fieldTypeDescriptor.decode(struct)
			parameters.add(value)
		}
		return try {
			val constructor = descriptor.cls.getConstructor(*parameters.map { it::class.java.unboxing() }
				.toTypedArray())
			constructor.newInstance(*parameters.toTypedArray())
		} catch(e: NoSuchMethodException) {
			try {
				val constructor = descriptor.cls.getConstructor(*parameters.map { it::class.java.unboxing() }
					.toTypedArray() + DefaultConstructorMarker::class.java)
				constructor.newInstance(*(parameters.toTypedArray() as Array<Any?> + null as Any?))
			} catch(e1: Exception) {
				e.addSuppressed(e1)
				throw e
			}
		} as T
	}

	override fun serialize(encoder: Encoder, value: T) {
		val struct = encoder.beginStructure(descriptor)
		for(i in 0 ..< descriptor.elementsCount) {
			val fieldTypeDescriptor = descriptor.getElementDescriptor(i)
			val field = descriptor.cls.getDeclaredField(descriptor.getElementName(i))
			field.trySetAccessible()
			fieldTypeDescriptor.encode(struct, field.get(value))
		}
	}
}

private fun Class<*>.unboxing(): Class<*> =
	when(this) {
		java.lang.Integer::class.java -> Int::class.java
		java.lang.Short::class.java -> Short::class.java
		java.lang.Byte::class.java -> Byte::class.java
		else -> this
	}

inline fun <reified T> serializer() = serializer<T>(T::class.java)

fun <T> serializer(cls: Class<out Any?>) =
	if(Modifier.isFinal(cls.modifiers))
		ReflectionSerializer(ReflectionSerialDescriptor(cls))
	else
		NotFinalClassesSerializer(cls as Class<T>)
