package com.greentree.commons.reflection.info

import com.greentree.commons.reflection.info.TypeInfoBuilder.getTypeInfo
import java.lang.reflect.Field
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.lang.reflect.TypeVariable
import java.lang.reflect.WildcardType
import kotlin.reflect.KClass

data object TypeInfoBuilder {

	@JvmStatic
	fun <C> getTypeInfo(field: Field): TypeInfo<C> {
		return getTypeInfo(field.genericType)
	}

	@Deprecated("", ReplaceWith("TypeInfoBuilder.getTypInfo(obj::class)"))
	@JvmStatic
	fun <T : Any> getTypeInfo(obj: T) = getTypeInfo(obj::class)

	fun <C : Any> getTypeInfo(cls: KClass<C>) = getTypeInfo(cls.java)

	@JvmStatic
	fun <C> getTypeInfo(cls: Class<C>): ClassInfo<C> {
		return ClassInfo.get(cls)
	}

	@JvmStatic
	fun <C> getTypeInfo(type: Type): TypeInfo<C> {
		return when(type) {
			is Class<*> -> getTypeInfo(type)
			is ParameterizedType -> getTypeInfo(type)
			is WildcardType -> getTypeInfo(type)
			is TypeVariable<*> -> getTypeInfo(Any::class.java)
			else -> throw UnsupportedOperationException(
				"type mast by instance of ParameterizedType or Class $type ${type.javaClass}"
			)
		} as TypeInfo<C>
	}

	fun <C> getTypeInfo(type: WildcardType): TypeInfo<C> {
		if(type.lowerBounds.isEmpty() && type.upperBounds.size == 1) return getTypeInfo(type.upperBounds[0])
		throw UnsupportedOperationException("$type " + type.lowerBounds.contentToString() + " " + type.upperBounds.contentToString())
	}

	fun <C, T : C> getTypeInfo(type: Class<C>, vararg parameters: Any): TypeInfo<T> {
		val types = Array(parameters.size) { i ->
			when(val p = parameters[i]) {
				is Type -> p
				is TypeInfo<*> -> p.type
				else -> throw IllegalArgumentException(p.toString() + "")
			}
		}
		return getTypeInfo(type, *types)
	}

	@JvmStatic
	fun <C, T : C> getTypeInfo(type: Class<C>, vararg parameters: Type): TypeInfo<T> {
		return getTypeInfo(GenericType.build(type, *parameters))
	}

	@JvmStatic
	fun <C, T : C> getTypeInfo(type: Class<C>, vararg parameters: TypeInfo<*>): TypeInfo<T> {
		val types = parameters.map { it.type }.toTypedArray()
		return getTypeInfo(type, *types)
	}

	@JvmStatic
	fun <C> getTypeInfo(type: ParameterizedType): TypeInfo<C> {
		if(type.actualTypeArguments.any { it is TypeVariable<*> })
			return getTypeInfo(type.rawType)
		return ParameterizedTypeInfo.get(type)
	}
}

val <T> Class<T>.type
	get() = getTypeInfo(this)
val <T : Any> KClass<T>.type
	get() = getTypeInfo(this)