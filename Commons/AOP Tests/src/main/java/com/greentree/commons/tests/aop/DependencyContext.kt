package com.greentree.commons.tests.aop

import org.junit.jupiter.params.provider.Arguments
import java.lang.reflect.Method
import java.util.stream.Stream

interface DependencyContext {

	operator fun <T> get(type: Class<T>): Collection<T>
}

fun DependencyContext.arguments(method: Method) = arguments(*method.parameterTypes)
fun DependencyContext.arguments(vararg types: Class<*>): Stream<out Arguments> {
	val result = mutableListOf<Arguments>()
	solve(types.iterator(), mutableListOf(), result)
	return result.stream()
}

private fun DependencyContext.solve(
	iterator: Iterator<Class<*>>,
	line: MutableCollection<Any>,
	result: MutableCollection<Arguments>,
) {
	if(iterator.hasNext()) {
		val cls = iterator.next()
		for(v in get(cls)) {
			line.add(v)
			solve(iterator, line, result)
			line.remove(v)
		}
	} else
		result.add(SimpleArguments(line.toTypedArray()))
}
