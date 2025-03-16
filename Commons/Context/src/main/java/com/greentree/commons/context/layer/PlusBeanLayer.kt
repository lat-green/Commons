package com.greentree.commons.context.layer

import com.greentree.commons.context.MutableBeanContext

data class PlusBeanLayer(val first: BeanLayer, val second: BeanLayer) : BeanLayer {

	override fun MutableBeanContext.register() {
		first.run {
			register()
		}
		second.run {
			register()
		}
	}
}

operator fun BeanLayer.plus(other: BeanLayer) = PlusBeanLayer(this, other)