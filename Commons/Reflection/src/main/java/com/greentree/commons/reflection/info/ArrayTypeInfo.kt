package com.greentree.commons.reflection.info

import java.lang.reflect.GenericArrayType

data class ArrayTypeInfo<T>(
	val componentType: TypeInfo<T>,
) : GenericArrayType, TypeInfo<Array<T>> {

	override fun getGenericComponentType() = componentType
	override fun toClass(): Class<Array<T>> {
		return componentType.toClass().arrayType() as Class<Array<T>>
	}

	override val typeArguments: Array<out TypeInfo<*>>
		get() = arrayOf()
	override val boxing
		get() = this

	override fun <S> complementChildOrNull(child: Class<S>): TypeInfo<S>? {
		TODO("Not yet implemented")
	}
}

fun <T> ArrayTypeInfo(type: GenericArrayType) = ArrayTypeInfo<T>(TypeInfo(type.genericComponentType))