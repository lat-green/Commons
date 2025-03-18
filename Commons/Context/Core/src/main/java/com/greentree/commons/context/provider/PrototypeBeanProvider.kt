package com.greentree.commons.context.provider

import com.greentree.commons.context.BeanContext
import com.greentree.commons.context.BeanRegistration
import com.greentree.commons.context.type

data class PrototypeBeanProvider<T>(
	val registration: BeanRegistration<T>,
	override val type: Class<out T> = registration.type,
) : BeanProvider<T> {

	override fun get(context: BeanContext): T = registration(context)
}