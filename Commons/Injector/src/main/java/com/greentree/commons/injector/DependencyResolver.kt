package com.greentree.commons.injector

import java.lang.reflect.Field
import java.lang.reflect.Parameter
import kotlin.reflect.KParameter

interface DependencyResolver {

	fun supportsDependency(dependency: Dependency): Boolean
	fun resolveDependency(dependency: Dependency): Any
}

fun DependencyResolver.resolveDependency(argument: KParameter) = resolveDependency(KParameterDependency(argument))
fun DependencyResolver.resolveDependency(argument: Parameter) = resolveDependency(ParameterDependency(argument))
fun DependencyResolver.resolveDependency(argument: Field) = resolveDependency(FieldDependency(argument))
