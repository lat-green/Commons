package com.greentree.commons.tests.aop

import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import java.util.stream.Stream

class AutowiredArgumentsProvider : ArgumentsProvider {

	override fun provideArguments(p0: ExtensionContext): Stream<out Arguments> {
		val context =
			ConfigClassDependencyContext(
				p0.testClass.get().getAnnotation(AutowiredConfig::class.java).value.java
			)
		return context.arguments(
			p0.testMethod.get()
		)
	}
}