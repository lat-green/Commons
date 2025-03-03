package com.greentree.commons.context.layer

import com.greentree.commons.context.BeanContext
import com.greentree.commons.context.registerSingleton

data class SingletonLayer(
	val type: Class<*>,
) : BeanLayer {

	override fun BeanContext.Builder.register() {
		registerSingleton(type)
	}
}