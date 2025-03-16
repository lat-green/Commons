package com.greentree.engine.rex.serialization.serializator.provider

import com.greentree.commons.serialization.context.SerializationContext
import com.greentree.commons.serialization.format.Decoder
import com.greentree.commons.serialization.format.Encoder
import com.greentree.commons.serialization.serializator.provider.SerializatorProvider
import com.greentree.commons.serialization.serializator.Serializator
import kotlin.reflect.KClass

data class KotlinObjectSerializator<T : Any>(
	val cls: KClass<T>
) : Serializator<T> {

	private val objectInstance = run {
		val v = cls.objectInstance
		require(v != null) { "$cls is not object" }
		v
	}

	override fun serialize(context: SerializationContext, encoder: Encoder, value: T) {
		encoder.beginStructure().use {
		}
	}

	override fun deserialize(context: SerializationContext, decoder: Decoder): T =
		decoder.beginStructure().use {
			objectInstance
		}

	companion object : SerializatorProvider {

		override val priority: Int
			get() = 1

		override fun <T : Any> provide(cls: Class<T>): Serializator<T>? {
			val kClass = cls.kotlin
			return if(kClass.objectInstance != null)
				KotlinObjectSerializator(kClass)
			else
				null
		}
	}

	override val type
		get() = cls.java
}