package com.greentree.engine.rex.fuse.tests

import com.greentree.commons.annotation.Annotations
import com.greentree.commons.context.BeanContext
import com.greentree.commons.context.layer.ContextBeanLayer
import com.greentree.commons.context.layer.registerLayer
import com.greentree.commons.injector.MethodCaller
import com.greentree.commons.injector.MethodCallerImpl
import com.greentree.commons.injector.newInstance
import com.greentree.commons.reflection.info.TypeUtil

fun resolveAnnotation(testClass: Class<*>): ContextTest? {
	var cls: Class<*>? = testClass
	while(cls != null) {
		val annotation = Annotations.filter(cls).getAnnotation(ContextTest::class.java)
		if(annotation != null)
			return annotation
		TypeUtil.getSuperClassAndInterfacesAsClass(cls).mapNotNull { resolveAnnotation(it) }.forEach {
			return it
		}
		cls = cls.declaringClass
	}
	return null
}

fun resolveBeanContext(testClass: Class<*>): BeanContext {
	require(ClassLoader.getSystemClassLoader() == testClass.classLoader)
	val annotation = resolveAnnotation(testClass)
		?: throw NullPointerException("annotation ContextTest on test $testClass")
	return BeanContext().apply {
		val simpleMethodCaller: MethodCaller = MethodCallerImpl()
		val layers = annotation.layers.map {
			simpleMethodCaller.newInstance(it)
		} + ContextBeanLayer
		registerLayer(layers)
	}
}