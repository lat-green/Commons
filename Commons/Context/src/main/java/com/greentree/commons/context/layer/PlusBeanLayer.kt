package com.greentree.commons.context.layer

import com.greentree.commons.context.BeanContext

data class PlusBeanLayer(val first: BeanLayer, val second: BeanLayer) : BeanLayer {

	override fun BeanContext.Builder.register() {
		first.run {
			register()
		}
		second.run {
			register()
		}
	}
}

operator fun BeanLayer.plus(other: BeanLayer) = PlusBeanLayer(this, other)