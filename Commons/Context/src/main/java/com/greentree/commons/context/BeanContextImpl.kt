package com.greentree.commons.context

import com.greentree.commons.reflection.info.TypeUtil
import com.greentree.commons.context.provider.BeanProvider

data class BeanContextImpl(
	val providers: Map<String, BeanProvider<*>>,
) : BeanContext {

	class Builder : BeanContext.Builder {

		private val providers = mutableMapOf<String, BeanProvider<*>>()
		private val result = BeanContextImpl(providers)

		override fun register(name: String, provider: BeanProvider<*>): BeanContext.Builder {
			if(name in providers)
				throw RuntimeException("duplicate name $name")
			providers[name] = provider
			return this
		}

		override fun build() = result
	}

	override fun <T> resolveProviderOrNull(name: String) = providers[name] as BeanProvider<T>?

	override fun <T> resolveAllProviders(type: Class<T>): Sequence<BeanProvider<T>> {
		return providers.values.filter { TypeUtil.isExtends(type, it.type) }.asSequence() as Sequence<BeanProvider<T>>
	}
}