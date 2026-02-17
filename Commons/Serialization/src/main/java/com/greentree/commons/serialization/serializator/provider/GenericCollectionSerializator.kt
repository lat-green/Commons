package com.greentree.commons.serialization.serializator.provider

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeUtil
import com.greentree.commons.serialization.serializator.AbstractCollectionSerializator
import com.greentree.commons.serialization.serializator.Serializator
import com.greentree.commons.serialization.serializator.getElementType
import java.lang.reflect.Modifier

data class GenericCollectionSerializator<E : Any>(
	override val type: TypeInfo<MutableCollection<E>>,
	override val elementType: TypeInfo<E> = type.getElementType(),
) : AbstractCollectionSerializator<E, MutableCollection<E>> {

	init {
		require(!Modifier.isAbstract(type.modifiers)) { "$type is abstract" }
	}

	private val constructor = try {
		type.toClass().getDeclaredConstructor()
	} catch(e: Throwable) {
		type.toClass().getDeclaredConstructor(Any::class.java.arrayType())
	}

	init {
		require(constructor.trySetAccessible()) { "$constructor" }
	}

	override fun newCollection() = if(constructor.parameterCount == 0)
		constructor.newInstance()
	else
		constructor.newInstance(arrayOf<Any>())

	companion object : SerializatorProvider {

		override val priority: Int
			get() = 5

		override fun <T : Any> provide(type: TypeInfo<out T>): Serializator<T>? {
			if(TypeUtil.isExtends(MutableCollection::class.java, type) && !Modifier.isAbstract(type.modifiers)) {
				type as TypeInfo<MutableCollection<Any>>
				return GenericCollectionSerializator(type) as Serializator<T>
			}
			return null
		}
	}
}