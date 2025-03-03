package com.greentree.commons.context.provider

import com.greentree.commons.context.BeanContext

data class InstanceBeanProvider<T : Any>(val instance: T) : BeanProvider<T> {

	override val type: Class<out T>
		get() = instance::class.java

	override fun get(context: BeanContext): T = instance
}