package com.greentree.commons.math.serializator

import com.greentree.commons.serialization.context.SerializationContext
import com.greentree.commons.serialization.context.manager
import com.greentree.commons.serialization.format.Decoder
import com.greentree.commons.serialization.format.Encoder
import com.greentree.commons.serialization.serializator.Serializator
import com.greentree.commons.serialization.serializator.manager.serializator
import org.joml.Vector3f

data object Vector3fSerializator : Serializator<Vector3f> {

	override fun serialize(context: SerializationContext, encoder: Encoder, value: Vector3f) {
		val fs = context.manager.serializator<Float>()
		encoder.beginStructure().use { struct ->
			struct.field("x").use { fs.serialize(context, it, value.x) }
			struct.field("y").use { fs.serialize(context, it, value.y) }
			struct.field("z").use { fs.serialize(context, it, value.z) }
		}
	}

	override fun deserialize(context: SerializationContext, decoder: Decoder): Vector3f {
		val fs = context.manager.serializator<Float>()
		return decoder.beginStructure().use { struct ->
			Vector3f(
				struct.fieldOrNull("x")?.use { fs.deserialize(context, it) } ?: 0f,
				struct.fieldOrNull("y")?.use { fs.deserialize(context, it) } ?: 0f,
				struct.fieldOrNull("z")?.use { fs.deserialize(context, it) } ?: 0f,
			)
		}
	}
}