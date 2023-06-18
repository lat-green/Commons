package com.greentree.commons.math.vector.list

class SectorVector1fCollection(private val min: Float, private val max: Float) : AbstractVector1fCollection {

	override val size: Int
		get() = 2

	override fun contains(element: Float) = element in min..max

	override fun iterator(): Iterator<Float> {
		return listOf(min, max).iterator()
	}

	override fun min(): Float {
		return min
	}

	override fun max(): Float {
		return max
	}
}
