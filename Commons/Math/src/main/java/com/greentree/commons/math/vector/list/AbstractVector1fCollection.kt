package com.greentree.commons.math.vector.list

import org.joml.Matrix2fc

interface AbstractVector1fCollection : Collection<Float> {

	override fun isEmpty() = 0 == size

	override fun containsAll(elements: Collection<Float>): Boolean {
		return elements.all { contains(it) }
	}

	operator fun times(other: Float): AbstractVector1fCollection {
		return MultiplyFloatVector1fCollection(this, other)
	}

	operator fun times(other: Matrix2fc): AbstractVector1fCollection {
		return MultiplyMatrix2fVector1fCollection(this, other)
	}

	fun max(): Float {
		return maxOf { it }
	}

	fun min(): Float {
		return minOf { it }
	}
}
