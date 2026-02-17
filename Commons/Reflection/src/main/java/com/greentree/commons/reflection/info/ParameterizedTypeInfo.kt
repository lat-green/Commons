package com.greentree.commons.reflection.info

import com.greentree.commons.reflection.ClassUtil
import com.greentree.commons.reflection.inject
import com.greentree.commons.util.mapToArray
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import kotlin.reflect.KClass

data class ParameterizedTypeInfo<T>(
	private val rawType0: Class<T>,
	private val actualTypeArguments0: Array<out TypeInfo<*>>,
) : ParameterizedType, TypeInfo<T> {

	init {
		require(!rawType.isArray) {
			"$rawType is Array"
		}
		require(rawType.typeParameters.size == actualTypeArguments.size) {
			"$rawType has ${rawType.typeParameters.size} typeParameters, but actualTypeArguments size is ${actualTypeArguments.size}"
		}
		val primitiveActualTypeArguments = actualTypeArguments.filter { it.isPrimitive }
		require(primitiveActualTypeArguments.isEmpty()) {
			"actualTypeArguments has primitive ${primitiveActualTypeArguments}"
		}
	}

	override fun toClass() = rawType

	override val typeArguments
		get() = actualTypeArguments
	override val boxing
		get() = if(actualTypeArguments.size > 0)
			this
		else
			TypeInfo(
				ClassUtil.getNotPrimitive(
					rawType
				)
			) as TypeInfo<T>

	override fun <S> complementChildOrNull(child: Class<S>): TypeInfo<S>? {
		if(!ClassUtil.isExtends(toClass(), child))
			return null
		if(child.typeParameters.size == 0)
			return TypeInfo(child)
		if(toClass() == child) {
			return this as TypeInfo<S>
		}
		return ClassUtil.getSuperClassAndInterfaces(child).mapNotNull {
			val c = complementChildOrNull<Any>(it as Class<Any>)
			if(c != null)
				TypeInfo<Any>(child.inject(it.typeParameters.map { it.name }.zip(c.typeArguments).associate { it }))
			else
				null
		}.firstOrNull() as TypeInfo<S>?
	}

	override fun getRawType() = rawType0

	override fun getActualTypeArguments() = actualTypeArguments0

	override fun getOwnerType() = null

	override fun equals(other: Any?): Boolean {
		if(this === other) return true
		if(javaClass != other?.javaClass) return false

		other as ParameterizedTypeInfo<*>

		if(rawType != other.rawType) return false
		if(!actualTypeArguments.contentEquals(other.actualTypeArguments)) return false

		return true
	}

	override fun hashCode(): Int {
		var result = rawType.hashCode()
		result = 31 * result + actualTypeArguments.contentHashCode()
		return result
	}

	override fun toString(): String {
		return if(actualTypeArguments.size > 0)
			"${rawType.name}<${actualTypeArguments.joinToString(", ")}>"
		else
			"${rawType.name}"
	}

	companion object {

		fun <T> fromClass(
			rawType: Class<T>,
			vararg arguments: Type,
		) =
			ParameterizedTypeInfo(
				rawType,
				arguments.mapToArray { TypeInfo<Any>(it) },
			)

		fun <T> fromClass(
			rawType: Class<T>,
			vararg arguments: KClass<*>,
		) =
			ParameterizedTypeInfo(
				rawType,
				arguments.mapToArray { TypeInfo(it) },
			)

		fun <T : Any> fromClass(
			rawType: KClass<T>,
			vararg arguments: Type,
		) =
			ParameterizedTypeInfo(
				rawType.java,
				arguments.mapToArray { TypeInfo<Any>(it) },
			)

		fun <T : Any> fromClass(
			rawType: KClass<T>,
			vararg arguments: KClass<*>,
		) =
			ParameterizedTypeInfo(
				rawType.java,
				arguments.mapToArray { TypeInfo(it) },
			)

		inline fun <reified T> fromClass(
			vararg arguments: Type,
		) = fromClass(T::class.java, *arguments)

		inline fun <reified T> fromClass(
			vararg arguments: KClass<*>,
		) = fromClass(T::class.java, *arguments)
	}
}

fun <T> ParameterizedTypeInfo(type: ParameterizedType) = when(type) {
	is ParameterizedTypeInfo<*> -> type as ParameterizedTypeInfo<T>
	else -> ParameterizedTypeInfo<T>(
		type.rawType as Class<T>,
		type.actualTypeArguments.mapToArray { TypeInfo<Any>(it) },
	)
}

