package com.greentree.commons.injector

import java.lang.reflect.Field
import java.lang.reflect.Parameter
import kotlin.reflect.KParameter

interface DependencyResolver {

	fun supportsDependency(dependency: Dependency): Boolean
	fun resolveDependency(dependency: Dependency): Any = resolveAllDependencies(dependency).run {
		val iter = iterator()
		if(!iter.hasNext())
			throw NoSuchElementException("no one resolver can not resolve dependency $dependency")
		val first = iter.next()
		if(iter.hasNext())
			throw NullPointerException("resolve dependency $dependency more one result ${toList()}")
		first
	}

	fun resolveAllDependencies(dependency: Dependency): Sequence<Any>
}

fun DependencyResolver.resolveDependency(argument: KParameter) = resolveDependency(KParameterDependency(argument))
fun DependencyResolver.resolveDependency(argument: Parameter) = resolveDependency(ParameterDependency(argument))
fun DependencyResolver.resolveDependency(argument: Field) = resolveDependency(FieldDependency(argument))
