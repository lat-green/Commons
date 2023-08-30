package com.greentree.commons.tests.aop

import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import java.util.stream.Stream
import kotlin.jvm.optionals.getOrNull

class AutowiredArgumentsProvider : ArgumentsProvider {

	override fun provideArguments(p0: ExtensionContext): Stream<out Arguments> {
		val context =
			ConfigClassDependencyContext(
				p0.testClass.getOrNull()!!.getAnnotation(AutowiredConfig::class.java).value.java
			)
		return context.arguments(
			p0.testMethod.getOrNull()!!,
			p0.testMethod.getOrNull()!!.getAnnotation(AutowiredTest::class.java).tags.toList()
		)
	}
}