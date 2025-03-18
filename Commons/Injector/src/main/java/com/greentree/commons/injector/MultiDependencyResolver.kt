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

	override fun resolveDependency(dependency: Dependency) = resolvers.filter {
		it.supportsDependency(dependency)
	}.map {
		it.resolveDependency(dependency)
	}.distinct().run {
		val iter = iterator()
		if(!iter.hasNext())
			throw NoSuchElementException("no one resolver can not resolve dependency $dependency")
		val first = iter.next()
		if(iter.hasNext())
			throw NullPointerException("resolve dependency $dependency more one result ${toList()}")
		first
	}
}
