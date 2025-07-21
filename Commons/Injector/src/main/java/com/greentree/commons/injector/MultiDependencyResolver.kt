package com.greentree.commons.injector

data class MultiDependencyResolver(
	val resolvers: Sequence<DependencyResolver>,
) : DependencyResolver {

	constructor(
		vararg resolvers: DependencyResolver,
	) : this(sequenceOf(*resolvers))

	constructor(
		resolvers: Iterable<DependencyResolver>,
	) : this(resolvers.asSequence())

	override fun supportsDependency(dependency: Dependency) = resolvers.any { it.supportsDependency(dependency) }

	override fun resolveAllDependencies(dependency: Dependency) = resolvers.filter {
		it.supportsDependency(dependency)
	}.flatMap {
		it.resolveAllDependencies(dependency)
	}.distinct()
}
