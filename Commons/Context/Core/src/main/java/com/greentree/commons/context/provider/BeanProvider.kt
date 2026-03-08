package com.greentree.commons.context.provider

import com.greentree.commons.context.BeanContext
import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeUtil

interface BeanProvider<T : Any> {

	val type: TypeInfo<out T>
		get() = TypeUtil.getFirstArgument(this::class, BeanProvider::class)

	fun get(context: BeanContext): T
}

