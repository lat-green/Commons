package com.greentree.commons.context.provider

import com.greentree.commons.context.BeanContext

object BeanContextPrototypeBeanProvider : BeanProvider<BeanContext> {

	override fun get(context: BeanContext) = context
}