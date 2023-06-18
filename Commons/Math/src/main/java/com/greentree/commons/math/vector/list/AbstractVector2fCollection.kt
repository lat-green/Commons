package com.greentree.commons.math.vector.list

import com.greentree.commons.math.vector.AbstractVector2f
import org.joml.Matrix2fc
import org.joml.Matrix3fc

interface AbstractVector2fCollection : Collection<AbstractVector2f> {

	override fun containsAll(elements: Collection<AbstractVector2f>): Boolean {
		return elements.all { contains(it) }
	}

	operator fun times(other: Float): AbstractVector2fCollection {
		return MultiplyFloatVector2fCollection(this, other)
	}

	operator fun times(other: Matrix3fc): AbstractVector2fCollection {
		return MultiplyMatrix3fVector2fCollection(this, other)
	}

	operator fun times(other: Matrix2fc): AbstractVector2fCollection {
		return MultiplyMatrix2fVector2fCollection(this, other)
	}

	fun getProjectionPoint(normal: AbstractVector2f): AbstractVector1fCollection {
		return ProjectionPointVector1fCollection(this, normal)
	}

	fun map(function: MathFunction<AbstractVector2f, AbstractVector2f>): AbstractVector2fCollection {
		return Function2fVector2fCollection(this, function)
	}

	fun map(function: MathFunction<AbstractVector2f, Float>): AbstractVector1fCollection {
		return Function2fVector1fCollection(this, function)
	}
}

fun AbstractVector2fCollection.times(other: Number): AbstractVector2fCollection {
	return times(other.toFloat())
}