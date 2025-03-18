package com.greentree.engine.rex.fuse.tests

import com.greentree.commons.annotation.Annotations
import com.greentree.commons.context.BeanContext
import com.greentree.commons.context.child
import com.greentree.commons.context.layer.FuseBeanLayer
import com.greentree.commons.context.layer.registerLayer
import com.greentree.commons.context.resolveBean
import com.greentree.commons.injector.MethodCaller
import com.greentree.commons.injector.newInstance
import com.greentree.commons.reflection.ClassUtil
import com.greentree.commons.reflection.info.TypeUtil
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.TestInstancePostProcessor
import java.lang.reflect.Modifier

private fun getAnnotation(cls: Class<*>): ContextTest? {
	val annotation = Annotations.filter(cls).getAnnotation(ContextTest::class.java)
	if(annotation != null)
		return annotation
	TypeUtil.getSuperClassAndInterfacesAsClass(cls).mapNotNull { getAnnotation(it) }.forEach {
		return it
	}
	return null
}

class ContextExtension : TestInstancePostProcessor {

	override fun postProcessTestInstance(testInstance: Any, context: ExtensionContext) {
		require(ClassLoader.getSystemClassLoader() == testInstance::class.java.classLoader)
		val annotation = getAnnotation(testInstance::class.java)
			?: throw NullPointerException("annotation FuseTest on test $testInstance")
		val parentBeanContext = BeanContext().apply {
			registerLayer(FuseBeanLayer)
		}
		val beanContext = parentBeanContext.child().apply {
			val simpleMethodCaller: MethodCaller = resolveBean()
			val layers = annotation.layers.map {
				simpleMethodCaller.newInstance(it)
			}
			registerLayer(layers)
		}
		val methodCaller: MethodCaller = beanContext.resolveBean()
		for(f in ClassUtil.getAllNotStaticFields(testInstance::class.java)
			.filter {
				!Modifier.isStatic(it.modifiers)
			}) {
			methodCaller.setField(f, testInstance)
		}
	}
}
