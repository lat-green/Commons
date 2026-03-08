package com.greentree.commons.reflection.info

import java.lang.reflect.Field
import java.lang.reflect.Parameter
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.lang.reflect.WildcardType
import kotlin.reflect.KClass

data object TypeInfoBuilder {

	@Deprecated("", ReplaceWith("TypeInfo<T>(field)", "com.greentree.commons.reflection.info.TypeInfo"))
	@JvmStatic
	fun <T : Any> getTypeInfo(field: Field): TypeInfo<T> {
		return TypeInfo<T>(field)
	}

	@Deprecated("", ReplaceWith("TypeInfo<T>(parameter)", "com.greentree.commons.reflection.info.TypeInfo"))
	@JvmStatic
	fun <T : Any> getTypeInfo(parameter: Parameter): TypeInfo<T> {
		return TypeInfo<T>(parameter)
	}

	@Deprecated("", ReplaceWith("TypeInfo(obj::class)", "com.greentree.commons.reflection.info.TypeInfo"))
	@JvmStatic
	fun <T : Any> getTypeInfo(obj: T) = getTypeInfo(obj::class)

	@Deprecated("", ReplaceWith("TypeInfo(cls)", "com.greentree.commons.reflection.info.TypeInfo"))
	fun <T : Any> getTypeInfo(cls: KClass<T>) = TypeInfo(cls)

	@Deprecated("", ReplaceWith("TypeInfo(cls)", "com.greentree.commons.reflection.info.TypeInfo"))
	@JvmStatic
	fun <T : Any> getTypeInfo(cls: Class<T>): TypeInfo<T> {
		return TypeInfo(cls)
	}

	@Deprecated("", ReplaceWith("TypeInfo<T>(type)", "com.greentree.commons.reflection.info.TypeInfo"))
	@JvmStatic
	fun <T : Any> getTypeInfo(type: Type): TypeInfo<T> {
		return TypeInfo<T>(type)
	}

	@Deprecated("", ReplaceWith("TypeInfo<T>(type)", "com.greentree.commons.reflection.info.TypeInfo"))
	fun <T : Any> getTypeInfo(type: WildcardType): TypeInfo<T> {
		return TypeInfo(type)
	}

	@Deprecated(
		"",
		ReplaceWith("TypeInfo.fromClass(type, *parameters)", "com.greentree.commons.reflection.info.TypeInfo")
	)
	@JvmStatic
	fun <T : Any> getTypeInfo(type: Class<T>, vararg parameters: Type): ParameterizedTypeInfo<T> {
		return ParameterizedTypeInfo.fromClass(type, *parameters)
	}

	@Deprecated(
		"",
		ReplaceWith("TypeInfo.fromClass(type, *parameters)", "com.greentree.commons.reflection.info.TypeInfo")
	)
	@JvmStatic
	fun <T : Any> getTypeInfo(type: Class<T>, vararg parameters: ParameterizedTypeInfo<*>): ParameterizedTypeInfo<T> {
		return ParameterizedTypeInfo.fromClass(type, *parameters)
	}

	@Deprecated("", ReplaceWith("TypeInfo<T>(type)", "com.greentree.commons.reflection.info.TypeInfo"))
	@JvmStatic
	fun <T : Any> getTypeInfo(type: ParameterizedType) = ParameterizedTypeInfo<T>(type)
}
