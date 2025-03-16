package com.greentree.commons.context.layer

import com.greentree.commons.context.MutableBeanContext
import com.greentree.commons.context.registerInstance

data class InstanceLayer(val instance: Any) : BeanLayer {

	override fun MutableBeanContext.register() {
		registerInstance(instance)
	}
}