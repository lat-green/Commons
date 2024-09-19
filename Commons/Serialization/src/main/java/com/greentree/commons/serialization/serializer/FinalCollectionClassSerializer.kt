package com.greentree.commons.serialization.serializer

import com.greentree.commons.serialization.data.Decoder
import com.greentree.commons.serialization.data.Encoder
import com.greentree.commons.serialization.data.decodeSerializable
import com.greentree.commons.serialization.data.encodeSerializable
import com.greentree.commons.serialization.descriptor.IntSerialDescriptor
import com.greentree.commons.serialization.descriptor.ReflectionSerialDescriptor
import com.greentree.commons.serialization.descriptor.SerialDescriptor
import com.greentree.commons.serialization.descriptor.descriptor

class FinalCollectionClassSerializer<T : Any>(
	private val cls: Class<out T>
) : Serializer<T> {

	override val descriptor = SerialDescriptor.builder("size-value")
		.element("size", IntSerialDescriptor)
		.element("value", cls.descriptor)
		.build()
	private val elementDescriptor = ReflectionSerialDescriptor(cls)

	override fun deserialize(decoder: Decoder): T {
		val collection = try {
			cls.getConstructor().newInstance()!! as MutableCollection<Any>
		} catch(_: Exception) {
			ArrayList<Any>()
		}
		decoder.beginStructure(descriptor).use { struct ->
			val size = struct.field(0).decodeInt()
			struct.field(1).beginCollection(descriptor).use { coll ->
				repeat(size) {
					val element = coll.field(it).decodeSerializable(Any::class)
					collection.add(element)
				}
			}
		}
		return collection as T
	}

	override fun serialize(encoder: Encoder, value: T) {
		val collection = value as Collection<Any>
		encoder.beginStructure(descriptor).use { struct ->
			struct.field(0).encodeInt(collection.size)
			struct.field(1).beginCollection(CollectionSerialDescriptor(collection, elementDescriptor)).use { coll ->
				var index = 0
				for(e in collection)
					coll.field(index++).encodeSerializable(Any::class, e)
			}
		}
	}
}
