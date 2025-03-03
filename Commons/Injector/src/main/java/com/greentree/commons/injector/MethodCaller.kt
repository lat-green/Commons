package com.greentree.commons.injector

class MethodCaller(
	resolvers: Sequence<DependencyResolver>,
) : Injector {

	private val resolver = MultiDependencyResolver(resolvers)

	constructor(resolvers: Iterable<DependencyResolver>) : this(resolvers.asSequence())
	constructor(vararg resolvers: DependencyResolver) : this(sequenceOf(*resolvers))

	override fun resolve(
		dependency: Dependency,
	) = resolver.resolveArgument(dependency)

	override fun isSupports(dependency: Dependency) = resolver.supportsArgument(dependency)

	override fun builder() = Builder(resolver)

	class Builder(vararg resolvers: DependencyResolver) : Injector.Builder {

		private val resolvers = mutableListOf(*resolvers)

		override fun add(resolver: DependencyResolver): Builder {
			resolvers.add(resolver)
			return this
		}

		override fun build() = MethodCaller(resolvers)
	}
}