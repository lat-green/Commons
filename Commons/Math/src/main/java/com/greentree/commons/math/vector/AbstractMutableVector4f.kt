package com.greentree.commons.math.vector

interface AbstractMutableVector4f : AbstractVector4f, AbstractMutableVector4<Float>, AbstractMutableFloatVector {

	override fun toMutable(): AbstractMutableVector4f {
		return this
	}
}
