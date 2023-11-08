package com.greentree.commons.injector.scope

import com.greentree.commons.reflection.ClassUtil

interface Scope {

	fun <T> getProperty(cls: Class<T>): T?
	operator fun contains(cls: Class<*>): Boolean
}

class SingletonScope(private val value: Any) : Scope {

	private val valueClass = value::class.java

	override fun <T> getProperty(cls: Class<T>) =
		if(ClassUtil.isExtends(valueClass, cls))
			value as T
		else
			null

	override fun contains(cls: Class<*>) = ClassUtil.isExtends(valueClass, cls)
}

object EmptyScope : Scope {

	override fun <T> getProperty(cls: Class<T>) = null
	override fun contains(cls: Class<*>) = false
}

class SingletonLazyScope(initializer: () -> Any) : Scope {

	private val value = lazy(initializer)
	private val valueClass = value::class.java

	override fun <T> getProperty(cls: Class<T>) =
		if(ClassUtil.isExtends(valueClass, cls))
			value as T
		else
			null

	override fun contains(cls: Class<*>) = ClassUtil.isExtends(valueClass, cls)
}

class PrototypeScope(private val initializer: () -> Any) : Scope {

	private val valueClass = value::class.java
	private val value
		get() = initializer()

	override fun <T> getProperty(cls: Class<T>) =
		if(ClassUtil.isExtends(valueClass, cls))
			value as T
		else
			null

	override fun contains(cls: Class<*>) = ClassUtil.isExtends(valueClass, cls)
}

inline fun <reified T> Scope.get(): T = get(T::class.java)
fun <T> Scope.get(cls: Class<T>): T = getProperty(cls)!!

operator fun Scope.plus(parent: Scope) = ScopeWithParent(this, parent)

data class ScopeWithParent(val scope: Scope, val parent: Scope) : Scope {

	override fun <T> getProperty(cls: Class<T>) = scope.getProperty(cls) ?: parent.getProperty(cls)

	override fun contains(cls: Class<*>) = scope.contains(cls) || parent.contains(cls)
}

fun newScope(builder: ScopeBuilder.() -> Unit): Scope {
	var result: Scope = EmptyScope
	builder(object : ScopeBuilder {
		override fun <T> get(cls: Class<T>) = result.get(cls)

		override fun addSingleton(value: Any) {
			result += SingletonScope(value)
		}

		override fun addSingleton(value: () -> Any) {
			result += SingletonLazyScope(value)
		}

		override fun addPrototype(value: () -> Any) {
			result += PrototypeScope(value)
		}
	})
	return result
}

interface ScopeBuilder {

	fun <T> get(cls: Class<T>): T

	fun addSingleton(value: Any)
	fun addSingleton(value: () -> Any)
	fun addPrototype(value: () -> Any)
}
