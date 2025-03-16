package com.greentree.commons.serialization.serializator.provider

import com.greentree.commons.annotation.Annotations
import com.greentree.commons.reflection.ClassUtil
import com.greentree.commons.serialization.context.AnnotatedElementProperty
import com.greentree.commons.serialization.context.SerializationContext
import com.greentree.commons.serialization.context.manager
import com.greentree.commons.serialization.format.Decoder
import com.greentree.commons.serialization.format.Encoder
import com.greentree.commons.util.UnsafeUtil
import com.greentree.commons.serialization.serializator.Serializator
import java.lang.reflect.Modifier

data class UnsafeRealSerializator<T>(
	override val type: Class<T>,
) : Serializator<T> {

	init {
		require(!(type.isInterface || type.isEnum || type.isArray || type.isAnnotation)) { "$type not supported" }
	}

	override fun serialize(context: SerializationContext, encoder: Encoder, value: T) {
		val manager = context.manager
		encoder.beginStructure().use { struct ->
			for(field in fields(type)) {
				val offset = unsafe.objectFieldOffset(field)
				val value = unsafe.getObject(value, offset)
				struct.field(field.name).use {
					manager.serializator(field.type)
						.serialize(context + AnnotatedElementProperty(Annotations.filter(field)), it, value)
				}
			}
		}
	}

	override fun deserialize(context: SerializationContext, decoder: Decoder): T {
		val manager = context.manager
		val result = unsafe.allocateInstance(type) as T
		decoder.beginStructure().use { struct ->
			for(field in fields(type)) {
				val offset = unsafe.objectFieldOffset(field)
				val value = struct.field(field.name).use {
					manager.serializator(field.type)
						.deserialize(context + AnnotatedElementProperty(Annotations.filter(field)), it)
				}
				unsafe.getAndSetObject(result, offset, value)
			}
		}
		return result
	}

	companion object : SerializatorProvider {

		private val unsafe = UnsafeUtil.getUnsafeInstance()

		private fun fields(cls: Class<*>) = ClassUtil.getAllFields(cls)
			.asSequence()
			.filter { !Modifier.isStatic(it.modifiers) }

		override val priority: Int
			get() = 0

		override fun <T : Any> provide(
			cls: Class<T>
		) = if(cls.isInterface || cls.isEnum || cls.isArray || cls.isAnnotation)
			null
		else
			UnsafeRealSerializator(cls)
	}
}