package com.greentree.commons.context.layer

import com.greentree.commons.context.BeanContext
import com.greentree.commons.context.registerInstance

data class InstanceLayer(val instance: Any) : BeanLayer {

	override fun BeanContext.Builder.register() {
		registerInstance(instance)
	}
}