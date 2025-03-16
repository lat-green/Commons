package com.greentree.commons.tests.aop

import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
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
	}

	override fun provideArguments(p0: ExtensionContext): Stream<out Arguments> {
		val testClass = p0.testClass.get()
		val context = ConfigClassDependencyContext(
			getConfigClass(testClass)
		)
		return context.arguments(
			p0.testMethod.get()
		)
	}
}