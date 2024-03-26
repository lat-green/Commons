package com.greentree.commons.reflection.info

import java.io.Serializable
import java.lang.reflect.Type

interface TypeInfo<T> : Serializable {

	fun <R> asExtends(type: TypeInfo<R>): TypeInfo<R> {
		return TypeInfoBuilder.getTypeInfo(toClass(), *type.typeArguments) as TypeInfo<R>
	}

	fun toClass(): Class<T>

	val typeArguments: Array<TypeInfo<out Any>>
	val interfaces: Array<TypeInfo<in T>>
	val boxing: TypeInfo<T>
	val superType: TypeInfo<in T>?
	val simpleName: String
		get() = toClass().name
	val name: String
		get() = toClass().name
	val isPrimitive: Boolean
		get() = toClass().isPrimitive
	val isInterface: Boolean
		get() = toClass().isInterface

	fun isSuperOf(superType: TypeInfo<in T>): Boolean {
		return superType.isSuperTo(this)
	}

	fun isSuperTo(type: TypeInfo<out T>): Boolean

	val typeName: CharSequence
		get() = type.typeName
	val type: Type

	fun isInstance(obj: Any?): Boolean {
		return toClass().isInstance(obj)
	}
}

val <T> TypeInfo<T>.superTypes: Array<TypeInfo<in T>>
	get() = run {
		val superType = superType ?: return interfaces
		return interfaces + superType
	}