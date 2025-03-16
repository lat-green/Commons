package com.greentree.commons.context.provider

import com.greentree.commons.context.BeanContext
import com.greentree.commons.reflection.info.TypeUtil

interface BeanProvider<T> {

	val type
		get() = TypeUtil.getFirstArgument<BeanProvider<*>, T>(this::class, BeanProvider::class)

	fun get(context: BeanContext): T
}

