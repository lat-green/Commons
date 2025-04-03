package com.greentree.commons.tests.aop

import com.greentree.commons.reflection.info.TypeUtil.isExtends
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

		override fun <T> get(type: Class<T>, tags: Collection<String>): Iterable<T> {
			return object : Iterable<T> {
				override fun iterator(): Iterator<T> {
					if(running || !isExtends(type, method.returnType))
						return listOf<T>().iterator()
					val methodTags = method.getAnnotation(AutowiredProvider::class.java).tags.toList()
					if(!methodTags.containsAll(tags))
						return listOf<T>().iterator()
					running = true
					try {
						var arguments = this@ConfigClassDependencyContext.arguments(method)
						return arguments.map { method.invoke(configurationInstance, *it.get()) as T }.toList()
							.iterator()
					} finally {
						running = false
					}
				}
			}
		}
	}

	override fun <T> get(type: Class<T>, tags: Collection<String>): Iterable<T> {
		return object : Iterable<T> {
			override fun iterator(): Iterator<T> {
				val result = mutableSetOf<T>()
				for(ctx in contexts) {
					result.addAll(ctx[type, tags])
				}
				return result.iterator()
			}
		}
	}
}
