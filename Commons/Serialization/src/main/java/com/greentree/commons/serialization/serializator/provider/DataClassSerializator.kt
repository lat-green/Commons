package com.greentree.commons.serialization.serializator.provider

import com.greentree.commons.annotation.Annotations
import com.greentree.commons.serialization.context.AnnotatedElementProperty
import com.greentree.commons.serialization.context.SerializationContext
import com.greentree.commons.serialization.format.Decoder
import com.greentree.commons.serialization.format.Encoder
import com.greentree.commons.serialization.serializator.Serializator
import com.greentree.commons.serialization.serializator.manager.SerializatorManager
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

data class DataClassSerializator<T : Any>(
	val cls: KClass<T>,
) : Serializator<T> {

	private val primaryConstructor = cls.primaryConstructor ?: throw IllegalArgumentException("$cls is not data class")

	override fun serialize(context: SerializationContext, encoder: Encoder, value: T) {
		val manager = context[SerializatorManager]
		encoder.beginStructure().use { struct ->
			for(parameter in primaryConstructor.parameters) {
				val field = cls.java.getDeclaredField(parameter.name)
				val type = field.type as Class<Any>
				require(field.trySetAccessible()) { "$field not accessible" }
				val fieldValue = field.get(value)
				struct.field(field.name).use { fieldEncoder ->
					manager.serializator(type)
						.serialize(
							context + AnnotatedElementProperty(Annotations.filter(field)),
							fieldEncoder,
							fieldValue
						)
				}
			}
		}
	}

	override fun deserialize(context: SerializationContext, decoder: Decoder): T {
		val manager = context[SerializatorManager]
		val constructor = primaryConstructor
		val args = decoder.beginStructure().use { struct ->
			primaryConstructor.parameters.map { parameter ->
				val field = cls.java.getDeclaredField(parameter.name)
				val type = field.type as Class<Any>
				struct.field(field.name).use { fieldDecoder ->
					manager.serializator(type)
						.deserialize(
							context + AnnotatedElementProperty(Annotations.filter(field)),
							fieldDecoder
						)
				}
			}.toTypedArray()
		}
		return constructor.call(*args)
	}

	override val type: Class<T>
		get() = cls.java

	companion object : SerializatorProvider {

		override fun <T : Any> provide(cls: Class<T>): Serializator<T>? {
			val guaranteed = cls.kotlin
			if(!guaranteed.isData || guaranteed.objectInstance != null)
				return null
			return DataClassSerializator(guaranteed)
		}
	}
}
