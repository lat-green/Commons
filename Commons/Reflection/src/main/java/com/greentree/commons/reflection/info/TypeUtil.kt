package com.greentree.commons.reflection.info

import com.greentree.commons.reflection.info.TypeInfoBuilder.getTypeInfo
import kotlin.reflect.KClass

object TypeUtil {

	@JvmStatic
	fun isExtends(superType: Class<*>, type: TypeInfo<*>): Boolean {
		return isExtends(getTypeInfo(superType), type)
	}

	@JvmStatic
	fun isExtends(superType: TypeInfo<*>, type: TypeInfo<*>): Boolean {
		if(superType == type)
			return true
		return superType.isParentFor(type)
	}

	@JvmStatic
	fun isExtends(superType: TypeInfo<*>, type: Class<*>): Boolean {
		return isExtends(superType, getTypeInfo(type))
	}

	@JvmStatic
	fun isExtends(superType: Class<*>, type: Class<*>): Boolean {
		return isExtends(getTypeInfo(superType), getTypeInfo(type))
	}

	@JvmStatic
	fun <S> getSuperType(type: TypeInfo<out S>, superClass: Class<S>): TypeInfo<S> =
		type.getSuperType(superClass) as TypeInfo<S>

	@JvmStatic
	fun <S, T> getFirstArgument(
		type: TypeInfo<out S>,
		superClass: Class<S>
	): Class<out T> = getSuperType(type, superClass).typeArguments[0].toClass() as Class<out T>

	@JvmStatic
	fun <S : Any, T> getFirstArgument(
		type: KClass<out S>,
		superClass: KClass<S>
	) = getFirstArgument<S, T>(getTypeInfo(type), superClass.java)
}
