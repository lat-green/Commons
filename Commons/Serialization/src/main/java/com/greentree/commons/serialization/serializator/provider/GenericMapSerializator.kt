package com.greentree.commons.serialization.serializator.provider

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeUtil
import com.greentree.commons.serialization.serializator.AbstractMapSerializator
import com.greentree.commons.serialization.serializator.Serializator
import java.lang.reflect.Modifier

data class GenericMapSerializator<K : Any, V : Any>(
	override val type: TypeInfo<MutableMap<K, V>>,
) : AbstractMapSerializator<K, V, MutableMap<K, V>> {

	private val constructor = type.toClass().getDeclaredConstructor()

	init {
		require(constructor.trySetAccessible()) { "$constructor" }
	}

	override fun newMap() = constructor.newInstance()

	companion object : SerializatorProvider {

		override val priority: Int
			get() = 5

		override fun <T : Any> provide(type: TypeInfo<out T>): Serializator<T>? {
			if(TypeUtil.isExtends(MutableMap::class.java, type) && !Modifier.isAbstract(type.modifiers)) {
				type as TypeInfo<MutableMap<Any, Any>>
				return GenericMapSerializator(type) as Serializator<T>
			}
			return null
		}
	}
}