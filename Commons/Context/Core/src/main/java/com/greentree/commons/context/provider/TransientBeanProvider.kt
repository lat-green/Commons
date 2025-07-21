package com.greentree.commons.context.provider

import com.greentree.commons.context.BeanContext
import com.greentree.commons.context.BeanRegistration
import com.greentree.commons.context.type

data class TransientBeanProvider<T>(
	val registration: BeanRegistration<T>,
	override val type: Class<out T> = registration.type,
) : BeanProvider<T> {

	private val instances = mutableMapOf<BeanContext, T>()

	override fun get(context: BeanContext): T = instances.getOrPut(context) {
		try {
			registration(context)
		} catch(e: Throwable) {
			throw RuntimeException("exception on register transient $type", e)
		}
	}
}