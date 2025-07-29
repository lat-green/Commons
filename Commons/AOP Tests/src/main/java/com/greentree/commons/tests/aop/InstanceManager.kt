package com.greentree.commons.tests.aop

import org.junit.platform.commons.util.ReflectionUtils
import kotlin.reflect.KClass

object InstanceManager {

	fun <T : Any> newInstance(cls: KClass<T>): T {
		return cls.objectInstance ?: ReflectionUtils.newInstance(cls.java)
	}

	fun <T : Any> newInstance(cls: Class<T>) = newInstance(cls.kotlin)
}