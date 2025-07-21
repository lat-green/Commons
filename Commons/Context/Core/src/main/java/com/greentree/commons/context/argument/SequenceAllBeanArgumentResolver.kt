package com.greentree.commons.context.argument

import com.greentree.commons.context.BeanContext
import com.greentree.commons.injector.Dependency
import com.greentree.commons.injector.DependencyResolver

class SequenceAllBeanDependencyResolver(
	private val beanContext: BeanContext,
) : DependencyResolver {

	override fun supportsDependency(dependency: Dependency) = dependency.type.toClass() == Sequence::class.java

	override fun resolveDependency(dependency: Dependency): Any {
		val type = dependency.type
		val t = type.typeArguments[0]
		return beanContext.resolveAllBeans(t.toClass())
	}

	override fun resolveAllDependencies(dependency: Dependency) = sequenceOf(resolveDependency(dependency))
}
