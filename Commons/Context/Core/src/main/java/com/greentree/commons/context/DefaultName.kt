package com.greentree.commons.context

import com.greentree.commons.reflection.info.TypeInfo
import java.util.*
import kotlin.reflect.KClass

fun defaultName(className: String): String {
	return firstToLowerCase(className.substringAfterLast('.'))
}

fun defaultName(cls: TypeInfo<*>) = defaultName(cls.toClass())

fun defaultName(cls: KClass<*>) = defaultName(cls.java)

fun defaultName(cls: Class<*>): String = defaultName(cls.name)

@Deprecated("", ReplaceWith("defaultName(obj.javaClass)"))
fun defaultName(obj: Any) = defaultName(obj.javaClass)

fun <T : Any> defaultName(factory: BeanRegistration<T>) = defaultName(factory.type)

private fun firstToLowerCase(str: String): String {
	if(str.isBlank())
		return str
	var first = str.substring(0, 1)
	val other = str.substring(1)
	first = first.lowercase(Locale.getDefault())
	return first + other
}