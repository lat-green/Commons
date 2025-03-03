package com.greentree.commons.injector

data class MultiDependencyResolver(
	val resolvers: Sequence<DependencyResolver>,
) : DependencyResolver {

	constructor(
		vararg resolvers: DependencyResolver,
	) : this(sequenceOf(*resolvers))

	override fun supportsArgument(dependency: Dependency) = resolvers.any { it.supportsArgument(dependency) }

	override fun resolveArgument(dependency: Dependency) = resolvers.filter {
		it.supportsArgument(dependency)
	}.map {
		it.resolveArgument(dependency)
	}.firstOrNull() ?: throw NoSuchElementException("no one resolver can not resolve argument $dependency")
}