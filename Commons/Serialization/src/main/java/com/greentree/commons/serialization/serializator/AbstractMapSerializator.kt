package com.greentree.commons.serialization.serializator

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeUtil
import com.greentree.commons.serialization.context.SerializationContext
import com.greentree.commons.serialization.context.manager
import com.greentree.commons.serialization.format.Decoder
import com.greentree.commons.serialization.format.Encoder
import com.greentree.commons.serialization.serializator.manager.deserialize
import com.greentree.commons.serialization.serializator.manager.serialize
import java.util.*

interface AbstractMapSerializator<K : Any, V : Any, M : MutableMap<K, V>> : Serializator<M> {

	fun newMap(): M

	override fun serialize(
		context: SerializationContext,
		encoder: Encoder,
		value: M,
	) {
		val manager = context.manager
		val keySerializator = manager.serializator(type.getKeyType())
		val valueSerializator = manager.serializator(type.getValueType())
		encoder.beginStructure().use { struct ->
			struct.field("size").use { f ->
				manager.serialize(context, f, value.size)
			}
			struct.field("entries").use { entriesField ->
				entriesField.beginCollection().use { struct ->
					var index = 0
					for(entry in value.entries) {
						struct.field(index++).use { f ->
							f.beginCollection().use { entryStruct ->
								entryStruct.field(0).use {
									keySerializator.serialize(context, it, entry.key)
								}
								entryStruct.field(1).use {
									valueSerializator.serialize(context, it, entry.value)
								}
							}
						}
					}
				}
			}
		}
	}

	override fun deserialize(
		context: SerializationContext,
		decoder: Decoder,
	): M {
		val manager = context.manager
		val keySerializator = manager.serializator(type.getKeyType())
		val valueSerializator = manager.serializator(type.getValueType())
		val map = newMap()
		map.run {
			decoder.beginStructure().use { struct ->
				val size = struct.field("size").use { f ->
					manager.deserialize<Int>(context, f)
				}
				struct.field("entries").use { entriesField ->
					entriesField.beginCollection().use { struct ->
						repeat(size) { index ->
							struct.field(index).use { f ->
								f.beginCollection().use { entryStruct ->
									val key = entryStruct.field(0).use {
										keySerializator.deserialize(context, it)
									}
									val value = entryStruct.field(1).use {
										valueSerializator.deserialize(context, it)
									}
									put(key, value)
								}
							}
						}
					}
				}
			}
		}
		return map
	}
}

fun <K> TypeInfo<out Map<K, *>>.getKeyType() = TypeUtil.getSuperType(this, Map::class.java)
	.typeArguments[0] as TypeInfo<K>

fun <V> TypeInfo<out Map<*, V>>.getValueType() = TypeUtil.getSuperType(this, Map::class.java)
	.typeArguments[1] as TypeInfo<V>

data object TreeMapSerializator : AbstractMapSerializator<Any, Any, TreeMap<Any, Any>> {

	override fun newMap() = TreeMap<Any, Any>()
}

data object HashMapSerializator : AbstractMapSerializator<Any, Any, HashMap<Any, Any>> {

	override fun newMap() = HashMap<Any, Any>()
}

data object LinkedHashMapSerializator : AbstractMapSerializator<Any, Any, LinkedHashMap<Any, Any>> {

	override fun newMap() = LinkedHashMap<Any, Any>()
}