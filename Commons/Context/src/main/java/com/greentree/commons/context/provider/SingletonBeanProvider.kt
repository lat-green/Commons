package com.greentree.commons.context.provider

import com.greentree.commons.context.BeanContext
import com.greentree.commons.context.BeanRegistration
import com.greentree.commons.context.resolveBean
import com.greentree.commons.context.type
import com.greentree.engine.rex.context.argument.MethodCaller
import java.lang.ref.SoftReference

private typealias Reference<T> = SoftReference<T>

data class SingletonBeanProvider<T>(
	val registration: BeanRegistration<T>,
	override val type: Class<out T> = registration.type,
) : BeanProvider<T> {

	private lateinit var instance: Reference<T>

	override fun get(context: BeanContext): T {
		if(::instance.isInitialized) {
			val value = instance.get()
			if(value != null)
				return value
		}
		val value = try {
			registration(context)
		} catch(e: Throwable) {
			throw RuntimeException("exception on register singleton $type", e)
		}
		instance = Reference(value)
		return value
	}
}

data class SingletonClassBeanProvider<T>(
	override val type: Class<T>,
) : BeanProvider<T> {

	private lateinit var instance: Reference<T>

	override fun get(context: BeanContext): T {
		if(::instance.isInitialized) {
			val value = instance.get()
			if(value != null)
				return value
		}
		val value = try {
			val methodCaller: MethodCaller = context.resolveBean()
			methodCaller.newInstance(type)
		} catch(e: Throwable) {
			throw RuntimeException("exception on register singleton $type", e)
		}
		instance = Reference(value)
		return value
	}
}