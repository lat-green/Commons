package com.greentree.commons.tests.aop

import org.junit.jupiter.params.provider.Arguments
import java.lang.reflect.Method
import java.util.stream.Stream

interface DependencyContext {

	operator fun <T> get(type: Class<T>, tags: Collection<String>): Collection<() -> T>

	fun count(type: Class<*>, tags: Collection<String>): Int = get(type, tags).size
}

fun DependencyContext.arguments(method: Method, tags: Collection<String> = listOf()) =
	arguments(tags, *method.parameterTypes)

fun DependencyContext.arguments(tags: Collection<String>, vararg types: Class<*>): Stream<out Arguments> {
	val result = mutableListOf<Arguments>()
	val count = types.map { count(it, tags) }.reduceOrNull { a, b -> a * b / gcd(a, b) } ?: 0
	solve(types.iterator(), mutableListOf(), result, tags)
	return result.stream()
}

private tailrec fun gcd(a: Int, b: Int): Int =
	if(b == 0)
		a
	else
		gcd(b, a % b)

fun DependencyContext.solve(
	iterator: Iterator<Class<*>>,
	line: MutableCollection<() -> Any>,
	result: MutableCollection<Arguments>,
	tags: Collection<String>,
) {
	if(iterator.hasNext()) {
		val cls = iterator.next()
		for(v in get(cls, tags)) {
			line.add(v)
			solve(iterator, line, result, tags)
			line.remove(v)
		}
	} else
		result.add(SimpleArguments(line.map { it() }.toTypedArray()))
}
