package com.greentree.commons.serialization.serializator

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeUtil
import com.greentree.commons.reflection.info.TypeUtil.getSuperType
import com.greentree.commons.serialization.context.SerializationContext
import com.greentree.commons.serialization.context.manager
import com.greentree.commons.serialization.format.Decoder
import com.greentree.commons.serialization.format.Encoder
import com.greentree.commons.serialization.serializator.manager.deserialize
import com.greentree.commons.serialization.serializator.manager.serialize
import com.greentree.commons.serialization.serializator.provider.SerializatorProvider

data class MapSerializator<K : Any, V : Any>(
	override val type: TypeInfo<out Map<K, V>>,
	val keyClass: TypeInfo<out K> = type.getKeyType(),
	val valueClass: TypeInfo<out V> = type.getValueType(),
) : Serializator<Map<K, V>> {

	override fun serialize(
		context: SerializationContext,
		encoder: Encoder,
		value: Map<K, V>,
	) {
		val manager = context.manager
		val keySerializator = manager.serializator(keyClass)
		val valueSerializator = manager.serializator(valueClass)
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
	): Map<K, V> {
		val manager = context.manager
		val keySerializator = manager.serializator(keyClass)
		val valueSerializator = manager.serializator(valueClass)
		return buildMap {
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
	}

	companion object : SerializatorProvider {

		override fun <T : Any> provide(type: TypeInfo<T>): Serializator<T>? {
			if(TypeUtil.isExtends(Map::class.java, type)) {
				return MapSerializator<Any, Any>(type as TypeInfo<out Map<Any, Any>>) as Serializator<T>
			}
			return null
		}
	}
}

private fun <K> TypeInfo<out Map<K, *>>.getKeyType() = getSuperType(this, Map::class.java)
	.typeArguments[0] as TypeInfo<K>

private fun <V> TypeInfo<out Map<*, V>>.getValueType() = getSuperType(this, Map::class.java)
	.typeArguments[1] as TypeInfo<V>
