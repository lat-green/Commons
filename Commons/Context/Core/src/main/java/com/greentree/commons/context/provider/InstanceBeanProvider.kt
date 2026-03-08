package com.greentree.commons.context.provider

import com.greentree.commons.context.BeanContext
import com.greentree.commons.reflection.info.TypeInfo

data class InstanceBeanProvider<T : Any>(val instance: T) : BeanProvider<T> {

	override val type: TypeInfo<out T>
		get() = TypeInfo(instance::class)

	override fun get(context: BeanContext): T = instance
}