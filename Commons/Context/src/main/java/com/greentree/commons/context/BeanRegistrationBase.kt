package com.greentree.commons.context

import com.greentree.commons.reflection.returnType

typealias BeanRegistration<T> = BeanContext.() -> T & Any

val <T> BeanRegistration<T>.type
	get() = returnType(this)
