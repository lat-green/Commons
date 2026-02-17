package com.greentree.commons.serialization.serializator

import com.greentree.commons.reflection.info.ParameterizedTypeInfo
import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeUtil
import com.greentree.commons.serialization.context.SerializationContext
import com.greentree.commons.serialization.context.manager
import com.greentree.commons.serialization.format.Decoder
import com.greentree.commons.serialization.format.Encoder
import com.greentree.commons.serialization.format.NamedDecoder
import com.greentree.commons.serialization.format.NamedEncoder
import com.greentree.commons.serialization.serializator.manager.deserialize
import com.greentree.commons.serialization.serializator.manager.serialize
import com.greentree.commons.serialization.serializator.provider.SerializatorProvider
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
		if(encoder is NamedEncoder && type.getKeyType() == TypeInfo.STRING) {
			encoder.beginStructure().use { struct ->
				for(entry in value.entries) {
					struct.field(entry.key.toString()).use { f ->
						valueSerializator.serialize(context, f, entry.value)
					}
				}
			}
		} else {
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
			if(decoder is NamedDecoder && type.getKeyType() == TypeInfo.STRING) {
				decoder.beginStructure().use { struct ->
					for(key in struct.keys) {
						val value = struct.field(key).use {
							valueSerializator.deserialize(context, it)
						}
						put(key as K, value)
					}
				}
			} else {
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
		return map
	}
}

fun <K> TypeInfo<out Map<K, *>>.getKeyType() = TypeUtil.getSuperType(this, Map::class.java)
	.typeArguments[0] as TypeInfo<K>

fun <V> TypeInfo<out Map<*, V>>.getValueType() = TypeUtil.getSuperType(this, Map::class.java)
	.typeArguments[1] as TypeInfo<V>

data class MapSerializator<K : Any, V : Any>(
	val keyType: TypeInfo<K>,
	val valueType: TypeInfo<V>,
) : AbstractMapSerializator<K, V, MutableMap<K, V>> {

	override val type
		get() = ParameterizedTypeInfo.fromClass<MutableMap<K, V>>(keyType, valueType)

	override fun newMap() = mutableMapOf<K, V>()

	companion object : SerializatorProvider {

		override fun <T : Any> provide(type: TypeInfo<out T>): Serializator<T>? {
			if(MutableMap::class.java == type.toClass()) {
				val keyType = type.typeArguments[0] as TypeInfo<Any>
				val valueType = type.typeArguments[1] as TypeInfo<Any>
				return MapSerializator(keyType, valueType) as Serializator<T>
			}
			return null
		}
	}
}

data object TreeMapSerializator : AbstractMapSerializator<Any, Any, TreeMap<Any, Any>> {

	override fun newMap() = TreeMap<Any, Any>()
}

data object HashMapSerializator : AbstractMapSerializator<Any, Any, HashMap<Any, Any>> {

	override fun newMap() = HashMap<Any, Any>()
}

data object LinkedHashMapSerializator : AbstractMapSerializator<Any, Any, LinkedHashMap<Any, Any>> {

	override fun newMap() = LinkedHashMap<Any, Any>()
}