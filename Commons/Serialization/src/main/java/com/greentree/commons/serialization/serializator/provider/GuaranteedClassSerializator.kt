package com.greentree.engine.rex.serialization.serializator.provider

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.serialization.context.SerializationContext
import com.greentree.commons.serialization.context.manager
import com.greentree.commons.serialization.format.Decoder
import com.greentree.commons.serialization.format.Encoder
import com.greentree.commons.serialization.serializator.Serializator
import com.greentree.commons.serialization.serializator.manager.realSerializator
import com.greentree.commons.serialization.serializator.manager.serializator
import com.greentree.commons.serialization.serializator.provider.SerializatorProvider
import com.greentree.commons.serialization.serializator.type.GuaranteedType
import java.lang.reflect.Modifier

data class GuaranteedClassSerializator<T : Any>(
	override val type: TypeInfo<out T>,
) : Serializator<T> {

	init {
		require(!Modifier.isFinal(type.modifiers)) { "$type is final" }
	}

	override fun serialize(context: SerializationContext, encoder: Encoder, value: T) {
		val manager = context.manager
		val cls = value::class.java as Class<T>
		val genericClass = type.complementChild(cls)
		encoder.beginStructure().use { struct ->
			struct.field("type").use { clsEncoder ->
				manager.serializator<Class<*>>()
					.serialize(context + GuaranteedType(type), clsEncoder, cls)
			}
			struct.field("value").use { dataEncoder ->
				manager.realSerializator(genericClass)
					.serialize(context, dataEncoder, value)
			}
		}
	}

	override fun deserialize(context: SerializationContext, decoder: Decoder): T {
		val manager = context.manager
		decoder.beginStructure().use { struct ->
			val cls = struct.field("type").use { clsEncoder ->
				manager.serializator<Class<T>>()
					.deserialize(context + GuaranteedType(type), clsEncoder)
			}
			val genericClass = type.complementChild(cls)
			struct.field("value").use { dataEncoder ->
				return manager.realSerializator(genericClass)
					.deserialize(context, dataEncoder)
			}
		}
	}

	companion object : SerializatorProvider {

		override fun <T : Any> provide(type: TypeInfo<out T>): Serializator<T>? {
			if(Modifier.isFinal(type.modifiers))
				return null
			return GuaranteedClassSerializator(type)
		}
	}
}
