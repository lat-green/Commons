package com.greentree.commons.context

import com.greentree.commons.context.provider.BeanProvider

class EmptyBeanContext : BeanContext {

	override fun <T : Any> resolveAllProviders(type: Class<T>): Sequence<BeanProvider<T>> = sequenceOf()

	override fun <T : Any> resolveProviderOrNull(name: String): BeanProvider<T>? = null

	override fun <T : Any> resolveProviderOrNull(type: Class<T>): BeanProvider<T>? = null
}