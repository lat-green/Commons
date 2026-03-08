package com.greentree.commons.context.provider

import com.greentree.commons.context.BeanContext
import com.greentree.commons.context.BeanRegistration
import com.greentree.commons.context.resolveBean
import com.greentree.commons.context.type
import com.greentree.commons.injector.MethodCaller
import com.greentree.commons.injector.newInstance
import com.greentree.commons.reflection.info.TypeInfo
import java.lang.ref.SoftReference

private typealias Reference<T> = SoftReference<T>

data class SingletonBeanProvider<T : Any>(
	val registration: BeanRegistration<T>,
	override val type: TypeInfo<out T> = registration.type,
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

data class SingletonClassBeanProvider<T : Any>(
	override val type: TypeInfo<T>,
) : BeanProvider<T> {

	private lateinit var instance: Reference<T>

	override fun get(context: BeanContext): T {
		if(::instance.isInitialized) {
			val value = instance.get()
			if(value != null)
				return value
		}
		val methodCaller: MethodCaller = context.resolveBean()
		val value = try {
			methodCaller.newInstance(type)
		} catch(e: Throwable) {
			throw RuntimeException("exception on register singleton $type", e)
		}
		instance = Reference(value)
		return value
	}
}