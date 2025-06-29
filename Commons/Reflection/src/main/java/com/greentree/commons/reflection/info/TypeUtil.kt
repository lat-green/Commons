package com.greentree.commons.reflection.info

import com.greentree.commons.reflection.info.TypeInfoBuilder.getTypeInfo
import kotlin.reflect.KClass

object TypeUtil {

	@JvmStatic
	fun isExtends(superType: Class<*>, type: Class<*>): Boolean {
		return isExtends(getTypeInfo(superType), getTypeInfo(type))
	}

	@JvmStatic
	fun isExtends(superType: Class<*>, type: KClass<*>): Boolean {
		return isExtends(getTypeInfo(superType), getTypeInfo(type))
	}

	@JvmStatic
	fun isExtends(superType: Class<*>, type: TypeInfo<*>): Boolean {
		return isExtends(getTypeInfo(superType), type)
	}

	@JvmStatic
	fun isExtends(superType: KClass<*>, type: Class<*>): Boolean {
		return isExtends(getTypeInfo(superType), getTypeInfo(type))
	}

	@JvmStatic
	fun isExtends(superType: KClass<*>, type: KClass<*>): Boolean {
		return isExtends(getTypeInfo(superType), getTypeInfo(type))
	}

	@JvmStatic
	fun isExtends(superType: KClass<*>, type: TypeInfo<*>): Boolean {
		return isExtends(getTypeInfo(superType), type)
	}

	@JvmStatic
	fun isExtends(superType: TypeInfo<*>, type: Class<*>): Boolean {
		return isExtends(superType, getTypeInfo(type))
	}

	@JvmStatic
	fun isExtends(superType: TypeInfo<*>, type: KClass<*>): Boolean {
		return isExtends(superType, getTypeInfo(type))
	}

	@JvmStatic
	fun isExtends(superType: TypeInfo<*>, type: TypeInfo<*>): Boolean {
		if(superType == type)
			return true
		return superType.isParentFor(type)
	}

	@JvmStatic
	fun <S> getSuperType(type: TypeInfo<out S>, superClass: Class<S>): TypeInfo<S> =
		type.getSuperType(superClass) as TypeInfo<S>

	@JvmStatic
	fun <S, T> getFirstArgument(
		type: TypeInfo<out S>,
		superClass: Class<S>,
	): Class<out T> = getSuperType(type, superClass).typeArguments[0].toClass() as Class<out T>

	@JvmStatic
	fun <S : Any, T> getFirstArgument(
		type: TypeInfo<out S>,
		superClass: KClass<S>,
	): Class<out T> = getFirstArgument(type, superClass.java)

	@JvmStatic
	fun <S : Any, T> getFirstArgument(
		type: KClass<out S>,
		superClass: Class<S>,
	): Class<out T> = getFirstArgument(getTypeInfo(type), superClass)

	@JvmStatic
	fun <S : Any, T> getFirstArgument(
		type: KClass<out S>,
		superClass: KClass<S>,
	): Class<out T> = getFirstArgument(getTypeInfo(type), superClass.java)

	@JvmStatic
	fun <S : Any, T> getFirstArgument(
		type: Class<out S>,
		superClass: Class<S>,
	): Class<out T> = getFirstArgument(getTypeInfo(type), superClass)

	@JvmStatic
	fun <S : Any, T> getFirstArgument(
		type: Class<out S>,
		superClass: KClass<S>,
	): Class<out T> = getFirstArgument(getTypeInfo(type), superClass.java)

	fun <T> getSuperClassAndInterfacesAsClass(cls: Class<T>) = sequence {
		cls.superclass?.let { superClass ->
			yield(superClass)
		}
		for(i in cls.interfaces) {
			yield(i)
		}
	}
}
