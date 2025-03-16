package com.greentree.commons.context.layer

import com.greentree.commons.context.MutableBeanContext
import com.greentree.commons.context.registerSingleton

data class SingletonLayer(
	val type: Class<*>,
) : BeanLayer {

	override fun MutableBeanContext.register() {
		registerSingleton(type)
	}
}