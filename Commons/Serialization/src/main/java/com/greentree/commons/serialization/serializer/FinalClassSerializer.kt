package com.greentree.commons.serialization.serializer

import com.greentree.commons.reflection.ClassUtil
import com.greentree.commons.serialization.data.Decoder
import com.greentree.commons.serialization.data.Encoder
import com.greentree.commons.serialization.descriptor.ReflectionSerialDescriptor
import java.lang.reflect.Constructor
import kotlin.reflect.full.primaryConstructor

class FinalClassSerializer<T : Any>(
	private val cls: Class<out T>
) : Serializer<T> {

	override val descriptor = ReflectionSerialDescriptor(cls)

	init {
		require(cls.kotlin.primaryConstructor != null) { "type $cls is not supported" }
//		require(Modifier.isFinal(cls.modifiers)) { "type $cls is not final" }
	}

	override fun deserialize(decoder: Decoder): T {
		val parameters = mutableListOf<Any>()
		decoder.beginStructure(descriptor).use { struct ->
			for(parameter in cls.kotlin.primaryConstructor!!.parameters) {
				val field = cls.getDeclaredField(parameter.name)
				field.trySetAccessible()
				val serializer = serializer(field.type as Class<Any>)
				parameters.add(serializer.deserialize(struct.field(field.name)))
			}
		}
		return try {
			val constructor = cls.getSupportedConstructor(*parameters.map { it.javaClass.unboxing() }
				.toTypedArray())
			constructor.trySetAccessible()
			constructor.newInstance(*parameters.toTypedArray())
		} catch(e: NoSuchMethodException) {
			try {
				val constructor = cls.getSupportedConstructor(*parameters.map { it.javaClass.unboxing() }
					.toTypedArray() + kotlin.jvm.internal.DefaultConstructorMarker::class.java)
				constructor.trySetAccessible()
				constructor.newInstance(*(parameters.toTypedArray() as Array<Any?> + null as Any?))
			} catch(e1: Exception) {
				e.addSuppressed(e1)
				throw e
			}
		}
	}

	override fun serialize(encoder: Encoder, value: T) {
		encoder.beginStructure(descriptor).use { struct ->
			for(parameter in cls.kotlin.primaryConstructor!!.parameters) {
				val field = cls.getDeclaredField(parameter.name)
				field.trySetAccessible()
				val serializer = serializer(field.type as Class<Any>)
				serializer.serialize(struct.field(field.name), field.get(value))
			}
		}
	}
}

fun <T> Class<T>.getSupportedConstructor(vararg array: Class<*>): Constructor<T> {
	return declaredConstructors.first { constructor ->
		constructor.parameters.zip(array).all { (param, argument) -> ClassUtil.isExtends(param.type, argument) }
	} as Constructor<T>
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
