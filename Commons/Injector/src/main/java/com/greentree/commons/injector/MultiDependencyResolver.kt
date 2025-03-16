package com.greentree.commons.injector

data class MultiDependencyResolver(
	val resolvers: Sequence<DependencyResolver>,
) : DependencyResolver {

	constructor(
		vararg resolvers: DependencyResolver,
	) : this(sequenceOf(*resolvers))

	override fun supportsDependency(dependency: Dependency) = resolvers.any { it.supportsDependency(dependency) }

	override fun resolveDependency(dependency: Dependency) = resolvers.filter {
		it.supportsDependency(dependency)
	}.map {
		it.resolveDependency(dependency)
	}.firstOrNull() ?: throw NoSuchElementException("no one resolver can not resolve argument $dependency")
}