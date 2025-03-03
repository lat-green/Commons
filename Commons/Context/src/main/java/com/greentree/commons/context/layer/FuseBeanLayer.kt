package com.greentree.commons.context.layer

import com.greentree.commons.context.BeanContext
import com.greentree.commons.context.provider.BeanContextPrototypeBeanProvider
import com.greentree.commons.context.registerSingleton
import com.greentree.commons.context.registerTransient
import com.greentree.commons.context.resolveAllBeans
import com.greentree.commons.context.resolveBean
import com.greentree.engine.rex.context.argument.BeanArgumentResolver
import com.greentree.engine.rex.context.argument.EnvironmentArgumentResolver
import com.greentree.engine.rex.context.argument.MethodCaller
import com.greentree.engine.rex.context.argument.SequenceAllBeanArgumentResolver
import com.greentree.engine.rex.context.environment.MapEnvironment

object FuseBeanLayer : BeanLayer {

	override fun BeanContext.Builder.register() {
		register("beanContext", BeanContextPrototypeBeanProvider)
		registerTransient {
			MethodCaller(resolveAllBeans())
		}
		registerTransient {
			BeanArgumentResolver(resolveBean())
		}
		registerTransient {
			SequenceAllBeanArgumentResolver(resolveBean())
		}

		registerInstance("environment", MapEnvironment())
		registerSingleton {
			EnvironmentArgumentResolver(resolveBean())
		}
	}
}