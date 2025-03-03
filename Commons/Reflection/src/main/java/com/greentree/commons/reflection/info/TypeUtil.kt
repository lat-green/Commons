package com.greentree.commons.reflection.info

import com.greentree.commons.reflection.info.TypeInfoBuilder.getTypeInfo
import kotlin.reflect.KClass

object TypeUtil {

	fun isExtends(superType: Class<*>, type: TypeInfo<*>): Boolean {
		return isExtends(getTypeInfo(superType), type)
	}

	fun isExtends(superType: TypeInfo<*>, type: TypeInfo<*>): Boolean {
		if(superType == type)
			return true
		return superType.isParentFor(type)
	}

	fun isExtends(superType: TypeInfo<*>, type: Class<*>): Boolean {
		return isExtends(superType, getTypeInfo(type))
	}

	fun isExtends(superType: Class<*>, type: Class<*>): Boolean {
		return isExtends(getTypeInfo(superType), getTypeInfo(type))
	}

	fun <S> getSuperType(type: TypeInfo<out S>, superClass: Class<S>): TypeInfo<S> =
		type.getSuperType(superClass) as TypeInfo<S>

	fun <S : T, T> getFirstArgument(
		type: TypeInfo<out S>,
		superClass: Class<S>
	): Class<T> = getSuperType(type, superClass).typeArguments[0].toClass() as Class<T>

	fun <S : T, T : Any> getFirstArgument(
		type: KClass<out S>,
		superClass: KClass<S>
	) = getFirstArgument<S, T>(getTypeInfo(type), superClass.java)
}
