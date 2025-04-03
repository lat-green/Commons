package com.greentree.commons.math.serializator

import com.greentree.commons.context.MutableBeanContext
import com.greentree.commons.context.layer.BeanLayer
import com.greentree.commons.context.registerInstance
import com.greentree.commons.serialization.Serialization

data object MathSerialization : BeanLayer {

	override val dependencies
		get() = sequenceOf(Serialization)

	override fun MutableBeanContext.register() {
		/* Serializators */
		registerInstance(Vector2fSerializator)
		registerInstance(Vector3fSerializator)
		registerInstance(Vector4fSerializator)
		registerInstance(QuaternionfSerializator)
	}
}
