package com.greentree.engine.rex.fuse.tests

import com.greentree.commons.context.resolveBean
import com.greentree.commons.injector.MethodCaller
import com.greentree.commons.reflection.ClassUtil
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.TestInstancePostProcessor

class ContextExtension : TestInstancePostProcessor {

	override fun postProcessTestInstance(testInstance: Any, context: ExtensionContext) {
		val beanContext = resolveBeanContext(testInstance::class.java)
		val methodCaller: MethodCaller = beanContext.resolveBean()
		for(f in ClassUtil.getAllNotStaticFields(testInstance::class.java)) {
			methodCaller.setFieldIfResolve(f, testInstance)
		}
	}
}
