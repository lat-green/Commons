package com.greentree.commons.tests.aop

import org.junit.jupiter.params.provider.Arguments
import java.lang.reflect.Method
import java.util.stream.Stream

interface DependencyContext {

	operator fun <T> get(type: Class<T>, tags: Collection<String>): Iterable<T>
}

fun DependencyContext.arguments(method: Method) = run {
	val a = method.parameters.map {
		it.type!! to (it.getAnnotation(AutowiredArgument::class.java)?.tags?.toList() ?: listOf())
	}
	arguments(a)
}

fun DependencyContext.arguments(types: List<Pair<Class<*>, Collection<String>>>): Stream<out Arguments> {
	val values = types.map { (cls, tags) -> get(cls, tags) }
	return solve(values).stream()
}

fun DependencyContext.solve(
	values: List<Iterable<Any>>,
): Collection<Arguments> {
	var result = listOf(listOf<Any>())

	for(v in values)
		result = result.flatMap {
			v.map { e ->
				it + e
			}
		}
	
	return result.map {
		Arguments.of(*it.toTypedArray())
	}
}
