package com.greentree.commons.context

import com.greentree.commons.context.provider.BeanProvider
import com.greentree.commons.reflection.info.TypeUtil

class BeanContextImpl : MutableBeanContext {

	private val providers: MutableMap<String, BeanProvider<*>> = mutableMapOf()

	override fun register(name: String, provider: BeanProvider<*>): MutableBeanContext {
		if(name in providers)
			throw RuntimeException("duplicate name $name")
		providers[name] = provider
		return this
	}

	override fun <T> resolveProviderOrNull(name: String) = providers[name] as BeanProvider<T>?

	override fun <T> resolveAllProviders(type: Class<T>): Sequence<BeanProvider<T>> {
		return providers.values.asSequence().filter { TypeUtil.isExtends(type, it.type) } as Sequence<BeanProvider<T>>
	}
}