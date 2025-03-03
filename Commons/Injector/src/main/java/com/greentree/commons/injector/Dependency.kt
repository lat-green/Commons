package com.greentree.commons.injector

import com.greentree.commons.annotation.Annotations
import com.greentree.commons.annotation.filter
import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeInfoBuilder.getTypeInfo
import com.greentree.commons.reflection.info.TypeUtil.*
import java.lang.reflect.AnnotatedElement
import java.lang.reflect.Field
import java.lang.reflect.Parameter
import kotlin.reflect.KClass
import kotlin.reflect.KParameter

sealed interface Dependency : AnnotatedElement {

	fun isSupportedType(type: TypeInfo<*>): Boolean = isExtends(this.type, type)

	val name: String?
	val type: TypeInfo<*>
	val isMarkedNullable: Boolean
		get() = false
	val isOptional: Boolean
		get() = false

	companion object {

		fun of(field: Field) = FieldDependency(field)
		fun of(parameter: Parameter) = ParameterDependency(parameter)
		fun of(parameter: KParameter) = KParameterDependency(parameter)
		fun of(cls: Class<*>, name: String? = null) = SimpleDependency(cls, name)
		fun of(cls: KClass<*>, name: String? = null) = SimpleDependency(cls, name)
		fun of(type: TypeInfo<*>, name: String? = null) = SimpleDependency(type, name)
	}
}

fun Dependency.isSupportedType(cls: Class<*>) = isSupportedType(getTypeInfo(cls))

data class FieldDependency(val field: Field) : Dependency, AnnotatedElement by Annotations.filter(field) {

	override val name: String
		get() = this.field.qualifierOrName
	override val type: TypeInfo<*>
		get() = getTypeInfo<Any>(this.field.genericType)
}

data class KParameterDependency(val parameter: KParameter) : Dependency,
	AnnotatedElement by Annotations.filter(parameter) {

	override val name: String?
		get() = parameter.qualifierOrName
	override val type: TypeInfo<*>
		get() = getTypeInfo<Any>(parameter.type)
	override val isOptional: Boolean
		get() = parameter.isOptional
	override val isMarkedNullable: Boolean
		get() = parameter.type.isMarkedNullable
}

data class ParameterDependency(val parameter: Parameter) : Dependency,
	AnnotatedElement by Annotations.filter(parameter) {

	override val name: String?
		get() = this.parameter.qualifierOrName
	override val type: TypeInfo<*>
		get() = getTypeInfo<Any>(this.parameter.parameterizedType)
}

data class SimpleDependency(
	override val type: TypeInfo<*>,
	override val name: String? = null
) : Dependency {

	constructor(cls: Class<*>, name: String? = null) : this(getTypeInfo(cls), name)
	constructor(cls: KClass<*>, name: String? = null) : this(getTypeInfo(cls), name)

	override fun <T : Annotation?> getAnnotation(annotationClass: Class<T>): T? = null

	override fun getAnnotations(): Array<Annotation> = arrayOf()

	override fun getDeclaredAnnotations(): Array<Annotation> = arrayOf()
}