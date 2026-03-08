package com.greentree.commons.context.provider

import com.greentree.commons.context.BeanContext
import com.greentree.commons.context.BeanRegistration
import com.greentree.commons.context.type
import com.greentree.commons.reflection.info.TypeInfo

data class PrototypeBeanProvider<T : Any>(
	val registration: BeanRegistration<T>,
	override val type: TypeInfo<out T> = registration.type,
) : BeanProvider<T> {

	override fun get(context: BeanContext): T = registration(context)
}