package com.greentree.commons.context.provider

import com.greentree.commons.context.BeanContext
import com.greentree.commons.context.BeanRegistration
import com.greentree.commons.context.type
import com.greentree.commons.reflection.ProxyUtil

data class ProxySingletonBeanProvider<T : Any>(
	val registration: BeanRegistration<T>,
	override val type: Class<out T> = registration.type,
) : BeanProvider<T> {

	private lateinit var proxy: T
	private lateinit var instance: T

	override fun get(context: BeanContext): T {
		if(!::instance.isInitialized) {
			if(::proxy.isInitialized) {
				return proxy
			}
			if(type.isInterface)
				proxy = ProxyUtil.newLazyInstance(type) { instance }
			else {
				val interfaces = sequence {
					var cls: Class<*>? = type
					while(cls != null) {
						yield(cls)
						cls = cls.superclass
					}
				}.flatMap { it.interfaces.asSequence() }.toList().toTypedArray()
				if(interfaces.isNotEmpty()) {
					proxy = ProxyUtil.newLazyInstance(*interfaces) { instance } as T
				}
			}
			instance = try {
				registration(context)
			} catch(e: Throwable) {
				throw RuntimeException("exception on register singleton $type", e)
			}
		}
		return instance
	}
}