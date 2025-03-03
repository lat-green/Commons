package com.greentree.commons.injector

import java.lang.reflect.Field
import java.lang.reflect.Parameter

interface DependencyResolver {

	fun supportsArgument(dependency: Dependency): Boolean
	fun resolveArgument(dependency: Dependency): Any
}

fun DependencyResolver.resolveArgument(argument: Parameter) = resolveArgument(ParameterDependency(argument))
fun DependencyResolver.resolveArgument(argument: Field) = resolveArgument(FieldDependency(argument))
