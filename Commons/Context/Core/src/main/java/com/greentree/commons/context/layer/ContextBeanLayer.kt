package com.greentree.commons.context.layer

import com.greentree.commons.context.MutableBeanContext
import com.greentree.commons.context.argument.BeanDependencyResolver
import com.greentree.commons.context.argument.EnvironmentDependencyResolver
import com.greentree.commons.context.argument.SequenceAllBeanDependencyResolver
import com.greentree.commons.context.environment.EnvironmentImpl
import com.greentree.commons.context.provider.BeanContextPrototypeBeanProvider
import com.greentree.commons.context.registerSingleton
import com.greentree.commons.context.registerTransient
import com.greentree.commons.context.resolveAllBeans
import com.greentree.commons.context.resolveBean
import com.greentree.commons.injector.MethodCallerImpl

object ContextBeanLayer : BeanLayer {

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

		registerSingleton("environment") {
			EnvironmentImpl(resolveAllBeans())
		}
		registerSingleton {
			EnvironmentDependencyResolver(resolveBean())
		}
	}
}