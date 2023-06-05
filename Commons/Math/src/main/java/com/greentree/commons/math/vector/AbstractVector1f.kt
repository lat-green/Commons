package com.greentree.commons.math.vector

interface AbstractVector1f : AbstractFloatVector, AbstractVector1<Float> {

	override fun toMutable(): AbstractMutableVector1f {
		return Vector1f(x)
	}

	companion object {

		val X: AbstractVector1f = FinalVector1f(1f)
	}
}