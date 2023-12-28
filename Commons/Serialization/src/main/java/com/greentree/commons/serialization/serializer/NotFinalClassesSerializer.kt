package com.greentree.commons.serialization.serializer

import com.greentree.commons.serialization.Decoder
import com.greentree.commons.serialization.Encoder
import com.greentree.commons.serialization.descriptor.SerialDescriptor
import java.lang.reflect.Modifier

class NotFinalClassesSerializer<T : Any>(private val cls: Class<T>) : Serializer<T> {

	init {
		require(!Modifier.isFinal(cls.modifiers)) { "type $cls is final" }
	}

	override val descriptor: SerialDescriptor<T>
		get() = TODO("Not yet implemented")

	override fun deserialize(decoder: Decoder): T {
		TODO("Not yet implemented")
	}

	override fun serialize(encoder: Encoder, value: T) {
		encoder.encodeString(value::class.java.simpleName)
		serializer(value.javaClass).serialize(encoder, value)
	}
}