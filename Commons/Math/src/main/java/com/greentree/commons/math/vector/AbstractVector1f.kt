package com.greentree.commons.math.vector

import com.greentree.commons.math.Mathf

interface AbstractVector1f : AbstractFloatVector, AbstractVector1<Float> {

	override fun toMutable(): AbstractMutableVector1f {
		return Vector1f(x)
	}

	fun lerp(other: AbstractVector1f, k: Float): AbstractVector1f {
		return vec1f(Mathf.lerp(x, other.x, k))
	}

	companion object {

		val X: AbstractVector1f = FinalVector1f(1f)
	}
}

fun vec1f(other: AbstractVector<out Float>): AbstractVector1f {
	require(other.size == 1) { "the size of the vectors are different $other.size != 1" }
	return vec1f(other[0])
}

fun vec1f(x: Float): AbstractVector1f {
	return FinalVector1f(x)
}