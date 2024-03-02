package com.greentree.commons.tests.aop

import org.junit.jupiter.params.provider.Arguments
import java.lang.reflect.Method
import java.util.*
import java.util.stream.Stream

interface DependencyContext {

	operator fun <T> get(type: Class<T>, tags: Collection<String>): Collection<T>

	fun count(type: Class<*>, tags: Collection<String>): Int = get(type, tags).size
}

fun DependencyContext.arguments(method: Method) = run {
	val a = method.parameters.map {
		it.type!! to (it.getAnnotation(AutowiredArgument::class.java)?.tags?.toList() ?: listOf())
	}
	val s = arguments(a).toList()
//	println(method)
//	println(a)
//	println(s)
//	println()
	s.stream()
}

fun DependencyContext.arguments(types: List<Pair<Class<*>, Collection<String>>>): Stream<out Arguments> {
	val values = types.map { (cls, tags) -> get(cls, tags) }
	return solve(values).stream()
}

fun DependencyContext.solve(
	values: List<Collection<Any>>,
	index: Int = 0,
	line: Stack<Any> = Stack(),
	result: MutableCollection<Arguments> = mutableListOf(),
): Collection<Arguments> {
	if(index < values.size) {
		for(v in values[index]) {
			line.push(v)
			solve(values, index + 1, line, result)
			line.pop()
		}
	} else
		result.add(SimpleArguments(line.toTypedArray()))
	return result
}
