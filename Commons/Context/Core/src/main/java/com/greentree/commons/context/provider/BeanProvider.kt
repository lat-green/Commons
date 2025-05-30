package com.greentree.commons.context.provider

import com.greentree.commons.context.BeanContext
import com.greentree.commons.reflection.info.TypeUtil

interface BeanProvider<T> {

	val type: Class<out T>
		get() = TypeUtil.getFirstArgument(this::class, BeanProvider::class)

	fun get(context: BeanContext): T
}

