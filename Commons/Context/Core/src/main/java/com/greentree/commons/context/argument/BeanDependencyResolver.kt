package com.greentree.commons.context.argument

import com.greentree.commons.annotation.Annotations
import com.greentree.commons.context.BeanContext
import com.greentree.commons.context.resolveBean
import com.greentree.commons.injector.Dependency
import com.greentree.commons.injector.DependencyResolver
import com.greentree.commons.injector.Qualifier

class BeanDependencyResolver(
	private val beanContext: BeanContext,
) : DependencyResolver {

	override fun supportsDependency(argument: Dependency): Boolean {
		if(Sequence::class.java == argument.type.toClass())
			return false
		val qualifier = Annotations.filter(argument).getAnnotation(Qualifier::class.java)
		if(qualifier != null)
			return beanContext.containsBean(qualifier.name, argument.type.toClass())
		return beanContext.containsBean(argument.type.toClass())
	}

	override fun resolveDependency(argument: Dependency): Any {
		if(Sequence::class.java == argument.type.toClass())
			TODO("Sequence not supported $argument")
		val qualifier = Annotations.filter(argument).getAnnotation(Qualifier::class.java)
		if(qualifier != null)
			return beanContext.resolveBean(qualifier.name, argument.type.toClass())!!
		return beanContext.resolveBean(argument.type.toClass())!!
	}
}
