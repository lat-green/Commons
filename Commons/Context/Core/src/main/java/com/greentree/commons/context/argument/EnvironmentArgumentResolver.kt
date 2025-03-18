package com.greentree.commons.context.argument

import com.greentree.commons.context.annotation.Value
import com.greentree.commons.context.environment.Environment
import com.greentree.commons.injector.Dependency
import com.greentree.commons.injector.DependencyResolver
import com.greentree.commons.reflection.info.TypeInfo

data class EnvironmentArgumentResolver(
	val environment: Environment,
) : DependencyResolver {

	override fun resolveDependency(dependency: Dependency): Any {
		val value = dependency.getAnnotation(Value::class.java)!!
		val result = environment[value.name] ?: if(value.default == Value.Companion.DEFAULT_DEFAULT)
			throw RuntimeException("environment property ${value.name} not found")
		else
			value.default
		try {
			return stringToAny(dependency.type, result)
		} catch(e: NumberFormatException) {
			throw RuntimeException("on resolve $dependency", e)
		}
	}

	override fun supportsDependency(dependency: Dependency) = dependency.isAnnotationPresent(
		Value::class.java
	)
}

private fun <T> stringToAny(type: TypeInfo<T>, str: String): T & Any = (when(type.toClass()) {
	java.lang.Long.TYPE -> str.toLong()
	Integer.TYPE -> str.toInt()
	java.lang.Short.TYPE -> str.toShort()
	Character.TYPE -> str.toInt().toChar()
	java.lang.Double.TYPE -> str.toDouble()
	java.lang.Float.TYPE -> str.toFloat()
	java.lang.Boolean.TYPE -> str.toBoolean()
	java.lang.Long::class.java -> str.toLong()
	Integer::class.java -> str.toInt()
	java.lang.Short::class.java -> str.toShort()
	Character::class.java -> str.toInt().toChar()
	java.lang.Double::class.java -> str.toDouble()
	java.lang.Float::class.java -> str.toFloat()
	java.lang.Boolean::class.java -> str.toBoolean()
	String::class.java -> str
	else -> TODO("$type")
} as T)!!
