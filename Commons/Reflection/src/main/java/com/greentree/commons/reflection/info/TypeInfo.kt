package com.greentree.commons.reflection.info

import com.greentree.commons.reflection.info.TypeInfoBuilder.getTypeInfo
import java.io.Serializable
import java.lang.reflect.Type

sealed interface TypeInfo<T> : Serializable {

	fun <R> asExtends(type: TypeInfo<R>): TypeInfo<R> {
		return getTypeInfo(toClass(), *type.typeArguments) as TypeInfo<R>
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

	fun isChildFor(parent: TypeInfo<*>): Boolean {
		return parent.isParentFor(this)
	}

	fun isParentFor(child: TypeInfo<*>): Boolean

	fun isChildFor(parent: Class<*>): Boolean = isChildFor(getTypeInfo(parent))
	fun isParentFor(child: Class<*>): Boolean = isParentFor(getTypeInfo(child))

	val typeName: CharSequence
		get() = type.typeName
	val type: Type

	fun isInstance(obj: Any?): Boolean {
		return toClass().isInstance(obj)
	}

	fun getSuperType(base: Class<*>): TypeInfo<*>? {
		if(base == toClass())
			return this
		if(!isChildFor(base))
			return null
		return getSuperClassAndInterfaces().mapNotNull {
			it.getSuperType(base)
		}.firstOrNull()
	}

	fun getSuperClassAndInterfaces(): Iterable<TypeInfo<in T>> = buildList {
		superType?.let {
			add(it)
		}
		addAll(interfaces)
	}

	fun getSuperClassAndInterfacesAsClass(): Iterable<Class<in T>> = getSuperClassAndInterfaces().map { it.toClass() }
}

val <T> TypeInfo<T>.superTypes: Array<TypeInfo<in T>>
	get() = run {
		val superType = superType ?: return interfaces
		return interfaces + superType
	}