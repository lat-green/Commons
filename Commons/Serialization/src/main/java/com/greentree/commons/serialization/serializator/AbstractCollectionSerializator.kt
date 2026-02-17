package com.greentree.commons.serialization.serializator

import com.greentree.commons.reflection.info.ParameterizedTypeInfo
import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeUtil
import com.greentree.commons.serialization.context.SerializationContext
import com.greentree.commons.serialization.context.manager
import com.greentree.commons.serialization.format.Decoder
import com.greentree.commons.serialization.format.Encoder
import com.greentree.commons.serialization.serializator.manager.deserialize
import com.greentree.commons.serialization.serializator.manager.serialize
import com.greentree.commons.serialization.serializator.provider.SerializatorProvider
import java.util.*

interface AbstractCollectionSerializator<E : Any, C : MutableCollection<E>> : Serializator<C> {

	val elementType: TypeInfo<E>
	override val type
		get() = ParameterizedTypeInfo.fromClass(MutableCollection::class, elementType) as TypeInfo<C>

	fun newCollection(): C

	override fun serialize(
		context: SerializationContext,
		encoder: Encoder,
		value: C,
	) {
		val manager = context.manager
		val elementSerializator = manager.serializator(elementType)
		encoder.beginStructure().use { struct ->
			struct.field("size").use { f ->
				manager.serialize(context, f, value.size)
			}
			struct.field("elements").use { entriesField ->
				entriesField.beginCollection().use { struct ->
					var index = 0
					for(element in value) {
						struct.field(index++).use { f ->
							elementSerializator.serialize(context, f, element)
						}
					}
				}
			}
		}
	}

	override fun deserialize(
		context: SerializationContext,
		decoder: Decoder,
	): C {
		val manager = context.manager
		val elementSerializator = manager.serializator(elementType)
		val collection = newCollection()
		collection.run {
			decoder.beginStructure().use { struct ->
				val size = struct.field("size").use { f ->
					manager.deserialize<Int>(context, f)
				}
				struct.field("elements").use { entriesField ->
					entriesField.beginCollection().use { struct ->
						repeat(size) { index ->
							struct.field(index).use { f ->
								val e = elementSerializator.deserialize(context, f)
								add(e)
							}
						}
					}
				}
			}
		}
		return collection
	}
}

fun <E> TypeInfo<out Collection<E>>.getElementType() = TypeUtil.getSuperType(this, Collection::class.java)
	.typeArguments[0] as TypeInfo<E>

data class ArrayListSerializator<E : Any>(
	override val elementType: TypeInfo<E>,
) : AbstractCollectionSerializator<E, ArrayList<E>> {

	override val type
		get() = ParameterizedTypeInfo.fromClass<ArrayList<E>>(elementType)

	override fun newCollection() = ArrayList<E>()

	companion object : SerializatorProvider {

		override fun <T : Any> provide(type: TypeInfo<out T>): Serializator<T>? {
			if(ArrayList::class.java == type.toClass()) {
				val elementType = type.typeArguments[0] as TypeInfo<Any>
				return ArrayListSerializator<Any>(elementType) as Serializator<T>
			}
			return null
		}
	}
}

data class LinkedListSerializator<E : Any>(
	override val elementType: TypeInfo<E>,
) : AbstractCollectionSerializator<E, LinkedList<E>> {

	override val type
		get() = ParameterizedTypeInfo.fromClass<LinkedList<E>>(elementType)

	override fun newCollection() = LinkedList<E>()

	companion object : SerializatorProvider {

		override fun <T : Any> provide(type: TypeInfo<out T>): Serializator<T>? {
			if(LinkedList::class.java == type.toClass()) {
				val elementType = type.typeArguments[0] as TypeInfo<Any>
				return LinkedListSerializator<Any>(elementType) as Serializator<T>
			}
			return null
		}
	}
}

data class CollectionSerializator<E : Any>(
	override val elementType: TypeInfo<E>,
) : AbstractCollectionSerializator<E, MutableCollection<E>> {

	override val type
		get() = ParameterizedTypeInfo.fromClass<MutableCollection<E>>(elementType)

	override fun newCollection() = mutableListOf<E>()

	companion object : SerializatorProvider {

		override fun <T : Any> provide(type: TypeInfo<out T>): Serializator<T>? {
			if(MutableCollection::class.java == type.toClass()) {
				val elementType = type.typeArguments[0] as TypeInfo<Any>
				return ListSerializator<Any>(elementType) as Serializator<T>
			}
			return null
		}
	}
}

data class ListSerializator<E : Any>(
	override val elementType: TypeInfo<E>,
) : AbstractCollectionSerializator<E, MutableList<E>> {

	override val type
		get() = ParameterizedTypeInfo.fromClass<MutableList<E>>(elementType)

	override fun newCollection() = mutableListOf<E>()

	companion object : SerializatorProvider {

		override fun <T : Any> provide(type: TypeInfo<out T>): Serializator<T>? {
			if(MutableList::class.java == type.toClass()) {
				val elementType = type.typeArguments[0] as TypeInfo<Any>
				return ListSerializator<Any>(elementType) as Serializator<T>
			}
			return null
		}
	}
}

data class SetSerializator<E : Any>(
	override val elementType: TypeInfo<E>,
) : AbstractCollectionSerializator<E, MutableSet<E>> {

	override val type
		get() = ParameterizedTypeInfo.fromClass<MutableSet<E>>(elementType)

	override fun newCollection() = mutableSetOf<E>()

	companion object : SerializatorProvider {

		override fun <T : Any> provide(type: TypeInfo<out T>): Serializator<T>? {
			if(MutableSet::class.java == type.toClass()) {
				val elementType = type.typeArguments[0] as TypeInfo<Any>
				return SetSerializator<Any>(elementType) as Serializator<T>
			}
			return null
		}
	}
}

