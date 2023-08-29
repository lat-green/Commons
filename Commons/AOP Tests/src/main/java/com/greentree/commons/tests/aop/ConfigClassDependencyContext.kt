package com.greentree.commons.tests.aop

import com.greentree.commons.reflection.ClassUtil.*
import java.lang.reflect.Method

class ConfigClassDependencyContext(configuration: Class<*>) : DependencyContext {

	private val contexts = mutableListOf<DependencyContext>()
	private val configurationInstance = configuration.getConstructor().newInstance()

	init {
		for(method in configuration.declaredMethods) {
			if(method.isAnnotationPresent(AutowiredProvider::class.java)) {
				contexts.add(ConfigMethodDependencyContext(method))
			}
		}
	}

	private inner class ConfigMethodDependencyContext(val method: Method) : DependencyContext {

		var running = false

		override fun <T> get(type: Class<T>): Collection<T> {
			if(running || !isExtends(type, method.returnType))
				return listOf()
			running = true
			try {
				var arguments = this@ConfigClassDependencyContext.arguments(method)
				return arguments.map { method.invoke(configurationInstance, *it.get()) }.toList() as Collection<T>
			} finally {
				running = false
			}
		}
	}

	override fun <T> get(type: Class<T>): Collection<T> {
		val result = mutableSetOf<T>()
		for(ctx in contexts) {
			result.addAll(ctx[type])
		}
		return result
	}
}
