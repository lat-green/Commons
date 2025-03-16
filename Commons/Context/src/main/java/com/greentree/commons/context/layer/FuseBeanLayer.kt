package com.greentree.commons.context.layer

import com.greentree.commons.context.MutableBeanContext
import com.greentree.commons.context.argument.BeanDependencyResolver
import com.greentree.commons.context.argument.EnvironmentArgumentResolver
import com.greentree.commons.context.argument.SequenceAllBeanDependencyResolver
import com.greentree.commons.context.environment.EnvironmentImpl
import com.greentree.commons.context.provider.BeanContextPrototypeBeanProvider
import com.greentree.commons.context.registerSingleton
import com.greentree.commons.context.registerTransient
import com.greentree.commons.context.resolveAllBeans
import com.greentree.commons.context.resolveBean
import com.greentree.commons.injector.MethodCallerImpl

object FuseBeanLayer : BeanLayer {

	override fun MutableBeanContext.register() {
		register("beanContext", BeanContextPrototypeBeanProvider)
		registerTransient {
			MethodCallerImpl(resolveAllBeans())
		}
		registerTransient {
			BeanDependencyResolver(resolveBean())
		}
		registerTransient {
			SequenceAllBeanDependencyResolver(resolveBean())
		}

		registerSingleton("environment", EnvironmentImpl::class)
		registerSingleton {
			EnvironmentArgumentResolver(resolveBean())
		}
	}
}