package com.greentree.engine.rex.fuse.tests

import com.greentree.commons.context.resolveBean
import com.greentree.commons.injector.MethodCaller
import com.greentree.commons.injector.resolveAll
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import java.util.stream.Stream

class AutowiredArgumentsProvider : ArgumentsProvider {

	override fun provideArguments(extensionContext: ExtensionContext): Stream<out Arguments> {
		val testClass = extensionContext.testClass.get()
		val testMethod = extensionContext.testMethod.get()
		val beanContext = resolveBeanContext(testClass)
		val methodCaller: MethodCaller = beanContext.resolveBean()
		val values = generate(testMethod.parameters.map {
			methodCaller.resolveAll(it).toList()
		}.iterator())

		return values.map {
			Arguments.of(*it.toTypedArray())
		}.stream()
	}
}

fun <T> generate(list: Iterator<List<T>>): List<List<T>> {
	if(list.hasNext()) {
		val l = list.next()
		return generate(list).flatMap {
			l.map { t ->
				listOf(t) + it
			}
		}
	}
	return listOf(listOf())
}