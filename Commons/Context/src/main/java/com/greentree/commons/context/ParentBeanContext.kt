package com.greentree.commons.context

import com.greentree.commons.context.provider.BeanProvider

data class ParentBeanContext(
	val parent: BeanContext,
	val child: BeanContext,
) : BeanContext {

	override fun <T> resolveProviderOrNull(type: Class<T>): BeanProvider<T>? =
		child.resolveProviderOrNull(type) ?: parent.resolveProviderOrNull(type)

	override fun <T> resolveAllProviders(type: Class<T>): Sequence<BeanProvider<T>> =
		child.resolveAllProviders(type) + parent.resolveAllProviders(type)

	override fun <T> resolveProviderOrNull(name: String): BeanProvider<T>? =
		child.resolveProviderOrNull(name) ?: parent.resolveProviderOrNull(name)

	class Builder(
		parent: BeanContext,
		private val child: BeanContext.Builder = BeanContext.Builder(),
	) : BeanContext.Builder {

		private val result = ParentBeanContext(parent, child.build())

		override fun build() = result

		override fun register(name: String, provider: BeanProvider<*>): BeanContext.Builder {
			child.register(name, provider)
			return this
		}
	}
}

fun BeanContext.child() = ParentBeanContext.Builder(this)
