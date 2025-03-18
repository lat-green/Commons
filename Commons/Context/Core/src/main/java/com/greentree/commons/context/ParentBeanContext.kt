package com.greentree.commons.context

import com.greentree.commons.context.provider.BeanProvider

data class ParentBeanContext(
	val parent: BeanContext,
	val child: MutableBeanContext = BeanContext(),
) : MutableBeanContext {

	override fun <T> resolveProviderOrNull(type: Class<T>): BeanProvider<T>? =
		child.resolveProviderOrNull(type) ?: parent.resolveProviderOrNull(type)

	override fun <T> resolveAllProviders(type: Class<T>): Sequence<BeanProvider<T>> =
		child.resolveAllProviders(type) + parent.resolveAllProviders(type)

	override fun <T> resolveProviderOrNull(name: String): BeanProvider<T>? =
		child.resolveProviderOrNull(name) ?: parent.resolveProviderOrNull(name)

	override fun register(name: String, provider: BeanProvider<*>): MutableBeanContext {
		child.register(name, provider)
		return this
	}
}

fun BeanContext.child() = ParentBeanContext(this)
