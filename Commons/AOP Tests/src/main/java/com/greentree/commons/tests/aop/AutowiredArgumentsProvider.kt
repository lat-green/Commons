package com.greentree.commons.tests.aop

import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import java.lang.reflect.Method
import java.util.stream.Stream

class AutowiredArgumentsProvider : ArgumentsProvider {

	companion object {

		@JvmStatic
		private fun getConfigClass(testClass: Class<*>): Class<*> {
			var cls: Class<*>? = testClass
			while(cls != null) {
				val annotation = cls.getAnnotation(AutowiredConfig::class.java)
				if(annotation != null)
					return annotation.value.java
				cls = cls.declaringClass
			}
			throw RuntimeException("Add AutowiredConfig to $testClass")
		}

		private fun getArgumentsProvider(testMethod: Method): ArgumentsProvider? {
			val annotation = testMethod.getAnnotation(AutowiredTest::class.java)
			if(annotation.value.java == AutowiredTest::class.java.getMethod("value").defaultValue) {
				return null
			}
			return InstanceManager.newInstance(annotation.value)
		}
	}

	override fun provideArguments(p0: ExtensionContext): Stream<out Arguments> {
		val testMethod = p0.testMethod.get()
		val testClass = p0.testClass.get()
		val baseArguments = getArgumentsProvider(testMethod)?.provideArguments(p0)
		val context = ConfigClassDependencyContext(
			getConfigClass(testClass)
		)
		if(baseArguments == null) {
			val aopArguments = context.arguments(
				p0.testMethod.get()
			)
			return aopArguments
		}
		val baseListArguments = baseArguments.toList()
		val size = getSize(baseListArguments)
		val aopArguments = context.arguments(
			p0.testMethod.get(), size
		)
		return aopArguments.eachWithEach(baseListArguments) { a, b ->
			Arguments {
				buildList {
					addAll(a.get())
					addAll(b.get())
				}.toTypedArray()
			}
		}
	}
}

private fun getSize(list: Iterable<Arguments>): Int? {
	val first = list.firstOrNull() ?: return null
	return first.get().size
}

inline fun <A, B, R> Stream<A>.eachWithEach(other: Collection<B>, crossinline block: (A, B) -> R) =
	flatMap { a ->
		other.stream().map { b ->
			block(a, b)
		}
	}