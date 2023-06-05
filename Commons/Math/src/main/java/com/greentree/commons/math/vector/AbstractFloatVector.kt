package com.greentree.commons.math.vector

import com.greentree.commons.math.Mathf
import com.greentree.commons.math.vector.AbstractVector.Companion.checkSize

interface AbstractFloatVector : AbstractVector<Float> {

	override fun dot(other: AbstractVector<out Float>): Float {
		checkSize(this, other)
		return zip(other).map { (x, y) -> x * y }.sum()
	}

	fun projection(normal: AbstractFloatVector): AbstractFloatVector {
		return normal.normalize(dot(normal))
	}

	override fun magnitude(length: Number): AbstractFloatVector {
		if (lengthSquared() < Mathf.EPS)
			return this
		return normalize(length)
	}

	override fun normalize(length: Number): AbstractFloatVector {
		return this * length / length()
	}

	operator fun plus(other: Number): AbstractFloatVector {
		return plus(other.toFloat())
	}

	operator fun div(other: Number): AbstractFloatVector {
		return div(other.toFloat())
	}

	operator fun times(other: Number): AbstractFloatVector {
		return times(other.toFloat())
	}

	operator fun minus(other: Number): AbstractFloatVector {
		return minus(other.toFloat())
	}

	override fun plus(other: Float): AbstractFloatVector {
		return plus(newVector(other))
	}

	override fun div(other: Float): AbstractFloatVector {
		return div(newVector(other))
	}

	override fun times(other: Float): AbstractFloatVector {
		return times(newVector(other))
	}

	override fun minus(other: Float): AbstractFloatVector {
		return minus(newVector(other))
	}

	override fun plus(other: AbstractVector<out Float>): AbstractFloatVector {
		checkSize(this, other)
		val dest = VectorNf(size)
		for (i in 0 until size) dest[i] = get(i) + other[i]
		return dest
	}

	override fun div(other: AbstractVector<out Float>): AbstractFloatVector {
		checkSize(this, other)
		val dest = VectorNf(size)
		for (i in 0 until size) dest[i] = get(i) / other[i]
		return dest
	}

	override fun times(other: AbstractVector<out Float>): AbstractFloatVector {
		checkSize(this, other)
		val dest = VectorNf(size)
		for (i in 0 until size) dest[i] = get(i) * other[i]
		return dest
	}

	override fun minus(other: AbstractVector<out Float>): AbstractFloatVector {
		checkSize(this, other)
		val dest = VectorNf(size)
		for (i in 0 until size) dest[i] = get(i) - other[i]
		return dest
	}

	override fun unaryMinus(): AbstractFloatVector {
		val dest = VectorNf(size)
		for (i in 0 until size) dest[i] = -get(i)
		return dest
	}

	override fun unaryPlus(): AbstractFloatVector {
		val dest = VectorNf(size)
		for (i in 0 until size) dest[i] = get(i)
		return dest
	}

	override fun lengthSquared(): Float {
		return this.map { it * it }.sum()
	}

	override fun toMutable(): AbstractMutableFloatVector {
		val dest = VectorNf(size)
		for (i in 0 until size) dest[i] = get(i)
		return dest
	}
}

operator fun Number.plus(other: AbstractVector<out Float>): AbstractFloatVector {
	return newVector(this, other.size) + other
}

operator fun Number.times(other: AbstractVector<out Float>): AbstractFloatVector {
	return newVector(this, other.size) * other
}

operator fun Number.minus(other: AbstractVector<out Float>): AbstractFloatVector {
	return newVector(this, other.size) - other
}

operator fun Number.div(other: AbstractVector<out Float>): AbstractFloatVector {
	return newVector(this, other.size) / other
}

fun newVector(value: Number, size: Int): AbstractFloatVector {
	return newVector(value.toFloat(), size)
}

fun AbstractFloatVector.newVector(value: Float): AbstractFloatVector {
	return OneComponentFloatVector(value, size)
}

fun AbstractFloatVector.newVector(value: Number): AbstractFloatVector {
	return newVector(value.toFloat(), size)
}

fun newVector(value: Float, size: Int): AbstractFloatVector {
	return OneComponentFloatVector(value, size)
}

class OneComponentFloatVector(val value: Float, override val size: Int) : AbstractFloatVector {

	override fun get(index: Int): Float = value
}