package com.greentree.commons.reflection

import java.lang.reflect.Modifier

object ClassUtil {

	@JvmStatic
	fun getAllSuperClasses(cls: Class<*>) = sequence {
		var c: Class<*>? = cls
		while(c != null && c != Any::class.java) {
			yield(c)
			c = c.superclass
		}
	}

	@JvmStatic
	fun getAllFields(cls: Class<*>) = getAllSuperClasses(cls).flatMap { it.declaredFields.asSequence() }

	@JvmStatic
	fun getAllFieldsHasAnnotation(
		cls: Class<*>,
		annotation: Class<out Annotation>,
	) = getAllFields(cls).filter { it.isAnnotationPresent(annotation) }

	@JvmStatic
	fun getAllNotStaticFields(cls: Class<*>) = getAllFields(cls).filterNot { Modifier.isStatic(it.modifiers) }

	@JvmStatic
	fun getDefault(type: Class<*>): Any? {
		if(type == Int::class.javaPrimitiveType || type == Int::class.javaObjectType) return 0
		if(type == Short::class.javaPrimitiveType || type == Short::class.javaObjectType) return 0.toShort()
		if(type == Boolean::class.javaPrimitiveType || type == Boolean::class.javaObjectType) return false
		if(type == Double::class.javaPrimitiveType || type == Double::class.javaObjectType) return 0.0
		if(type == Float::class.javaPrimitiveType || type == Float::class.javaObjectType) return 0.0f
		if(type == Char::class.javaPrimitiveType || type == Char::class.javaObjectType) return 0.toChar()
		if(type == Byte::class.javaPrimitiveType || type == Byte::class.javaObjectType) return 0.toByte()
		return null
	}

	@JvmStatic
	fun <T> getNotPrimitive(type: Class<T>): Class<T> {
		if(type == Float::class.javaPrimitiveType) return Float::class.javaObjectType as Class<T>
		if(type == Int::class.javaPrimitiveType) return Int::class.javaObjectType as Class<T>
		if(type == Double::class.javaPrimitiveType) return Double::class.javaObjectType as Class<T>
		if(type == Char::class.javaPrimitiveType) return Char::class.javaObjectType as Class<T>
		if(type == Byte::class.javaPrimitiveType) return Byte::class.javaObjectType as Class<T>
		if(type == Short::class.javaPrimitiveType) return Short::class.javaObjectType as Class<T>
		if(type == Boolean::class.javaPrimitiveType) return Boolean::class.javaObjectType as Class<T>
		if(type == Long::class.javaPrimitiveType) return Long::class.javaObjectType as Class<T>
		return type // not primitive
	}

	@JvmStatic
	fun isPrimitive(type: Class<*>): Boolean {
		return type.isPrimitive || type == Int::class.java || type == Short::class.java || type == Boolean::class.java || type == Double::class.java || type == Float::class.java || type == Char::class.java || type == Byte::class.java
	}

	@Deprecated(
		"use TypeUtil",
		ReplaceWith("TypeUtil.isExtends(superClass, cls)", { "com.greentree.commons.reflection.info.TypeUtil" })
	)
	@JvmStatic
	fun isExtends(superClass: Class<*>, cls: Class<*>): Boolean {
		if(superClass == cls) return true
		return superClass.isAssignableFrom(cls)
	}
}
