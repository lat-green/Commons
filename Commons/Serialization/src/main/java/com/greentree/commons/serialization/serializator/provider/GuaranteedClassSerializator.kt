package com.greentree.engine.rex.serialization.serializator.provider

import com.greentree.commons.serialization.context.SerializationContext
import com.greentree.commons.serialization.context.manager
import com.greentree.commons.serialization.format.Decoder
import com.greentree.commons.serialization.format.Encoder
import com.greentree.commons.serialization.serializator.Serializator
import com.greentree.commons.serialization.serializator.manager.serializator
import com.greentree.commons.serialization.serializator.provider.SerializatorProvider
import com.greentree.commons.serialization.serializator.type.GuaranteedType
import java.lang.reflect.Modifier

data class GuaranteedClassSerializator<T : Any>(
	val guaranteed: Class<T>,
) : Serializator<T> {

	init {
		require(!Modifier.isFinal(guaranteed.modifiers)) { "$guaranteed is final" }
	}

	override fun serialize(context: SerializationContext, encoder: Encoder, value: T) {
		val manager = context.manager
		val cls = value!!::class.java as Class<T>
		encoder.beginStructure().use { struct ->
			struct.field("type").use { clsEncoder ->
				manager.serializator<Class<*>>()
					.serialize(context + GuaranteedType(guaranteed), clsEncoder, cls)
			}
			struct.field("value").use { dataEncoder ->
				manager.realSerializator(cls)
					.serialize(context, dataEncoder, value)
			}
		}
	}

	override fun deserialize(context: SerializationContext, decoder: Decoder): T {
		val manager = context.manager
		decoder.beginStructure().use { struct ->
			val cls = struct.field("type").use { clsEncoder ->
				manager.serializator<Class<T>>()
					.deserialize(context + GuaranteedType(guaranteed), clsEncoder)
			}
			struct.field("value").use { dataEncoder ->
				return manager.realSerializator(cls)
					.deserialize(context, dataEncoder)
			}
		}
	}

	override val type
		get() = guaranteed as Class<T>

	companion object : SerializatorProvider {

		override fun <T : Any> provide(cls: Class<T>): Serializator<T>? {
			if(Modifier.isFinal(cls.modifiers))
				return null
			return GuaranteedClassSerializator(cls)
		}
	}
}
