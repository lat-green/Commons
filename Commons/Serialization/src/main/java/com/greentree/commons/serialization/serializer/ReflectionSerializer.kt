package com.greentree.commons.serialization.serializer

import com.greentree.commons.serialization.Decoder
import com.greentree.commons.serialization.Encoder
import com.greentree.commons.serialization.descriptor.ReflectionSerialDescriptor
import com.greentree.commons.serialization.descriptor.SerialDescriptor
import java.lang.reflect.Modifier
import kotlin.jvm.internal.DefaultConstructorMarker

class ReflectionSerializer<T : Any>(private val cls: Class<T>) : Serializer<T> {

	override val descriptor: SerialDescriptor<T>
		get() = ReflectionSerialDescriptor(cls)

	init {
		require(Modifier.isFinal(cls.modifiers)) { "type ${cls} is not final" }
	}

	override fun deserialize(decoder: Decoder): T {
		val struct = decoder.beginStructure(descriptor)
		val parameters = mutableListOf<Any>()
		for(i in 0 ..< descriptor.elementsCount) {
			val fieldTypeDescriptor = descriptor.getElementDescriptor(i)
			val value = fieldTypeDescriptor.decode(struct)!!
			parameters.add(value)
		}
		return try {
			val constructor = cls.getConstructor(*parameters.map { it.javaClass.unboxing() }
				.toTypedArray())
			constructor.newInstance(*parameters.toTypedArray())
		} catch(e: NoSuchMethodException) {
			try {
				val constructor = cls.getConstructor(*parameters.map { it.javaClass.unboxing() }
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
			val fieldTypeDescriptor = descriptor.getElementDescriptor(i) as SerialDescriptor<Any>
			val field = cls.getDeclaredField(descriptor.getElementName(i))
			field.trySetAccessible()
			fieldTypeDescriptor.encode(struct, field.get(value))
		}
	}
}

fun Class<*>.unboxing(): Class<*> =
	when(this) {
		java.lang.Long::class.java -> Long::class.java
		java.lang.Integer::class.java -> Int::class.java
		java.lang.Short::class.java -> Short::class.java
		java.lang.Byte::class.java -> Byte::class.java
		java.lang.Double::class.java -> Double::class.java
		java.lang.Float::class.java -> Float::class.java
		else -> this
	}
