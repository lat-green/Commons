package com.greentree.commons.math.vector

import com.greentree.commons.math.Mathf
import org.joml.Matrix4f

interface AbstractVector4f : AbstractFloatVector, AbstractVector4<Float> {

	override fun toMutable(): AbstractMutableVector4f {
		return Vector4f(x, y, z, w)
	}

	fun xy(): AbstractVector2f {
		return vec2f(x, y)
	}

	fun xz(): AbstractVector2f {
		return vec2f(x, z)
	}

	operator fun times(mat: Matrix4f): AbstractVector4f {
		return mat.transform(toJoml()).toMath()
	}

	fun toJoml() = org.joml.Vector4f(x, y, z, w)

	override fun magnitude(length: Number): AbstractVector4f {
		if(lengthSquared() < Mathf.EPS)
			return this
		return normalize(length)
	}

	override fun normalize(length: Number): AbstractVector4f {
		return this * (length.toFloat() / length())
	}

	override fun plus(other: Float): AbstractVector4f {
		return super.plus(other) as AbstractVector4f
	}

	override fun div(other: Float): AbstractVector4f {
		return super.div(other) as AbstractVector4f
	}

	override fun times(other: Float): AbstractVector4f {
		return super.times(other) as AbstractVector4f
	}

	override fun minus(other: Float): AbstractVector4f {
		return super.minus(other) as AbstractVector4f
	}

	override fun plus(other: Number): AbstractVector4f {
		return super.plus(other) as AbstractVector4f
	}

	override fun div(other: Number): AbstractVector4f {
		return super.div(other) as AbstractVector4f
	}

	override fun times(other: Number): AbstractVector4f {
		return super.times(other) as AbstractVector4f
	}

	override fun minus(other: Number): AbstractVector4f {
		return super.minus(other) as AbstractVector4f
	}

	override fun plus(other: AbstractVector<out Float>): AbstractVector4f {
		return plus(vec4f(other))
	}

	override fun times(other: AbstractVector<out Float>): AbstractVector4f {
		return times(vec4f(other))
	}

	override fun div(other: AbstractVector<out Float>): AbstractVector4f {
		return div(vec4f(other))
	}

	override fun minus(other: AbstractVector<out Float>): AbstractVector4f {
		return minus(vec4f(other))
	}

	fun plus(other: AbstractVector4f): AbstractVector4f {
		return vec4f(x + other.x, y + other.y, z + other.z, w + other.w)
	}

	fun minus(other: AbstractVector4f): AbstractVector4f {
		return vec4f(x - other.x, y - other.y, z - other.z, w - other.w)
	}

	fun times(other: AbstractVector4f): AbstractVector4f {
		return vec4f(x * other.x, y * other.y, z * other.z, w * other.w)
	}

	fun div(other: AbstractVector4f): AbstractVector4f {
		return vec4f(x / other.x, y / other.y, z / other.z, w / other.w)
	}

	override fun unaryPlus(): AbstractVector4f {
		return vec4f(this)
	}

	override fun unaryMinus(): AbstractVector4f {
		return vec4f(-x, -y, -z, -w)
	}

	companion object {

		val X: AbstractVector4f = FinalVector4f(1f, 0f, 0f, 0f)
		val Y: AbstractVector4f = FinalVector4f(0f, 1f, 0f, 0f)
		val Z: AbstractVector4f = FinalVector4f(0f, 0f, 1f, 0f)
		val W: AbstractVector4f = FinalVector4f(0f, 0f, 0f, 1f)
	}
}

fun vec4f(other: AbstractVector<out Float>): AbstractVector4f {
	require(other.size == 4) { "the size of the vectors are different $other.size != 4" }
	return vec4f(other[0], other[1], other[2], other[3])
}

fun vec4f(x: Float, y: Float, z: Float, w: Float): AbstractVector4f {
	return FinalVector4f(x, y, z, w)
}

fun org.joml.Vector4f.toMath(): AbstractVector4f {
	return vec4f(x, y, z, w)
}
