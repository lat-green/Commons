package com.greentree.commons.injector

class MethodCallerImpl(
	resolvers: Sequence<DependencyResolver>,
) : MethodCaller {

	private val resolver = MultiDependencyResolver(resolvers)

	constructor(resolvers: Iterable<DependencyResolver>) : this(resolvers.asSequence())
	constructor(vararg resolvers: DependencyResolver) : this(sequenceOf(*resolvers))

	override fun resolve(
		dependency: Dependency,
	) = resolver.resolveDependency(dependency)

	override fun resolveAll(
		dependency: Dependency,
	) = resolver.resolveAllDependencies(dependency)

	override fun isSupports(dependency: Dependency) = resolver.supportsDependency(dependency)

	override fun builder() = Builder(resolver)

	class Builder(vararg resolvers: DependencyResolver) : MethodCaller.Builder {

		private val resolvers = mutableListOf(*resolvers)

		override fun add(resolver: DependencyResolver): Builder {
			resolvers.add(resolver)
			return this
		}

		override fun build() = MethodCallerImpl(resolvers)
	}
}