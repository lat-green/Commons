package com.greentree.commons.reflection.info

import com.greentree.commons.reflection.ParameterizedTypeUtil
import com.greentree.commons.util.mapToArray
import com.greentree.commons.util.toTypedArray
import java.lang.reflect.Field
import java.lang.reflect.GenericArrayType
import java.lang.reflect.Parameter
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.lang.reflect.TypeVariable
import java.lang.reflect.WildcardType
import kotlin.reflect.KClass

sealed interface TypeInfo<T> : Type {

	fun toClass(): Class<T>
	val isPrimitive
		get() = toClass().isPrimitive
	val typeArguments: Array<out TypeInfo<*>>
	val superType: ParameterizedTypeInfo<in T>?
		get() = ParameterizedTypeUtil.getSuperType(this)?.let { TypeInfo<Any>(it) as ParameterizedTypeInfo<in T> }
	val interfaces: Array<out ParameterizedTypeInfo<in T>>
		get() = ParameterizedTypeUtil.getInterfaces(this)
			.map { TypeInfo<Any>(it) as ParameterizedTypeInfo<in T> }
			.toTypedArray()
	val simpleName: String
		get() = toClass().simpleName
	val name: String
		get() = toClass().name
	val boxing: TypeInfo<T>
	val modifiers
		get() = toClass().modifiers

	fun isInstance(obj: Any?): Boolean {
		return toClass().isInstance(obj)
	}

	fun isChildFor(parent: TypeInfo<*>): Boolean {
		return parent.isParentFor(this)
	}

	fun isParentFor(child: TypeInfo<*>): Boolean {
		return ParameterizedTypeUtil.isExtends(this, child)
	}

	fun isChildFor(parent: Class<*>): Boolean = isChildFor(TypeInfo(parent))
	fun isParentFor(child: Class<*>): Boolean = isParentFor(TypeInfo(child))

	fun <S> getSuperType(base: Class<S>) = ParameterizedTypeUtil.getBaseOrNull(this, base)?.let { TypeInfo<S>(it) }

	fun getSuperClassAndInterfaces(): Iterable<ParameterizedTypeInfo<in T>> = buildList {
		superType?.let {
			add(it)
		}
		addAll(interfaces)
	}

	fun getSuperClassAndInterfacesAsClass(): Iterable<Class<in T>> = getSuperClassAndInterfaces().map { it.toClass() }

	fun <S> complementChildOrNull(child: Class<S>): TypeInfo<S>?
	fun <S> complementChild(child: Class<S>): TypeInfo<S> =
		complementChildOrNull(child) ?: TypeInfo(child)

	companion object {

		val STRING = TypeInfo(String::class.java)
	}
}

fun <T> TypeInfo(field: Field) = TypeInfo<T>(field.genericType)
fun <T> TypeInfo(parameter: Parameter) = TypeInfo<T>(parameter.parameterizedType)

fun <T> TypeInfo(
	rawType: Class<T>,
): TypeInfo<T> = if(rawType.isArray)
	ArrayTypeInfo(TypeInfo<Any>(rawType.componentType)) as TypeInfo<T>
else
	ParameterizedTypeInfo(
		rawType,
		rawType.typeParameters.mapToArray { TypeInfo<Any>(it) }
	)

fun <T> TypeInfo(type: WildcardType): TypeInfo<T> {
	val upperBounds = type.upperBounds.toList() - Any::class.java
	if(upperBounds.isEmpty())
		return TypeInfo<T>(Any::class.java)
	if(type.lowerBounds.isEmpty() && upperBounds.size == 1)
		return TypeInfo(upperBounds[0])
	throw UnsupportedOperationException("$type " + type.lowerBounds.contentToString() + " " + type.upperBounds.contentToString())
}

fun <T> TypeInfo(type: Type): TypeInfo<T> = when(type) {
	is TypeInfo<*> -> type
	is Class<*> -> TypeInfo(type as Class<T>)
	is ParameterizedType -> ParameterizedTypeInfo(type)
	is GenericArrayType -> ArrayTypeInfo<Any>(type)
	is TypeVariable<*> -> TypeInfo<T>(type.bounds.firstOrNull() ?: Any::class.java)
	is WildcardType -> TypeInfo(type)
	else -> TODO("$type::class == ${type::class}")
} as TypeInfo<T>

fun <T : Any> TypeInfo(
	rawType: KClass<T>,
) = TypeInfo(rawType.java)

@Deprecated("", ReplaceWith("TypeInfo(this)"))
val <T> Class<T>.type
	get() = TypeInfo(this)

@Deprecated("", ReplaceWith("type"))
val <T> Class<T>.parameterizedType
	get() = type

@Deprecated("", ReplaceWith("TypeInfo(this)"))
val <T : Any> KClass<T>.type
	get() = TypeInfo(this)

