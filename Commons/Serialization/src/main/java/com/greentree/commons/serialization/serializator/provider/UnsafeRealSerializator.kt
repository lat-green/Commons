package com.greentree.commons.serialization.serializator.provider

import com.greentree.commons.annotation.Annotations
import com.greentree.commons.reflection.ClassUtil
import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeInfoBuilder.getTypeInfo
import com.greentree.commons.serialization.context.AnnotatedElementProperty
import com.greentree.commons.serialization.context.SerializationContext
import com.greentree.commons.serialization.context.manager
import com.greentree.commons.serialization.format.Decoder
import com.greentree.commons.serialization.format.Encoder
import com.greentree.commons.serialization.serializator.Serializator
import com.greentree.commons.util.UnsafeUtil
import java.lang.reflect.Modifier

data class UnsafeRealSerializator<T : Any>(
	override val type: TypeInfo<T>,
) : Serializator<T> {

	init {
		val type = type.toClass()
		require(!(type.isInterface || type.isEnum || type.isArray || type.isAnnotation || type.isPrimitive)) { "$type not supported" }
	}

	override fun serialize(context: SerializationContext, encoder: Encoder, value: T) {
		val manager = context.manager
		encoder.beginStructure().use { struct ->
			for(field in fields(type)) {
				val type = getTypeInfo<Any>(field)
				val offset = unsafe.objectFieldOffset(field)
				val fieldValue = unsafe.getObject(value, offset)
				struct.field(field.name).use {
					manager.serializator(type)
						.serialize(context + AnnotatedElementProperty(Annotations.filter(field)), it, fieldValue)
				}
			}
		}
	}

	override fun deserialize(context: SerializationContext, decoder: Decoder): T {
		val manager = context.manager
		val result = try {
			unsafe.allocateInstance(type.toClass())
		} catch(e: InstantiationException) {
			throw RuntimeException("$type", e)
		} as T
		decoder.beginStructure().use { struct ->
			for(field in fields(type)) {
				val type = getTypeInfo<Any>(field)
				val offset = unsafe.objectFieldOffset(field)
				val value = struct.field(field.name).use {
					manager.serializator(type)
						.deserialize(context + AnnotatedElementProperty(Annotations.filter(field)), it)
				}
				unsafe.getAndSetObject(result, offset, value)
			}
		}
		return result
	}

	companion object : SerializatorProvider {

		private val unsafe = UnsafeUtil.getUnsafeInstance()

		private fun fields(cls: TypeInfo<*>) = ClassUtil.getAllFields(cls.toClass())
			.asSequence()
			.filter { !Modifier.isStatic(it.modifiers) }

		override val priority: Int
			get() = 0

		override fun <T : Any> provide(
			type: TypeInfo<T>,
		) = run {
			val cls = type.toClass()
			if(cls.isInterface || cls.isEnum || cls.isArray || cls.isAnnotation || cls.isPrimitive)
				null
			else
				UnsafeRealSerializator(type)
		}
	}
}