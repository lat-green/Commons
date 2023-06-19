package com.greentree.commons.math.vector

import com.greentree.commons.math.Mathf
import com.greentree.commons.math.Mathf.Companion.lerp
import org.joml.Matrix3f
import org.joml.Matrix4f
import org.joml.Vector4f

interface AbstractVector3f : AbstractFloatVector, AbstractVector3<Float> {

	override fun toMutable(): AbstractMutableVector3f {
		return Vector3f(x, y, z)
	}

	fun lerp(other: AbstractVector3f, k: Float): AbstractVector3f {
		return vec3f(lerp(x, other.x, k), lerp(y, other.y, k), lerp(z, other.z, k))
	}

	fun xy(): AbstractVector2f {
		return vec2f(x, y)
	}

	fun xz(): AbstractVector2f {
		return vec2f(x, z)
	}

	fun cross(other: AbstractVector3f): AbstractVector3f {
		val result = Vector3f()
		result.x = y * other.z - z * other.y
		result.y = z * other.x - x * other.z
		result.z = x * other.y - y * other.x
		return result
	}

	operator fun times(mat: Matrix3f): AbstractVector3f {
		return mat.transform(toJoml())!!.toMath()
	}

	operator fun times(mat: Matrix4f): AbstractVector3f {
		return mat.transform(Vector4f(x, y, z, 1f)).vec3()
	}

	fun toJoml(): org.joml.Vector3f {
		return org.joml.Vector3f(x, y, z)
	}

	fun getProjection(normal: AbstractVector3f): AbstractVector2f {
		val a = normal.x()
		val b = normal.y()
		val c = normal.z()
		var xAxis: AbstractVector3f
		var yAxis: AbstractVector3f = vec3f(a, b, c).normalize()
		xAxis = vec3f(b, -a, 0f)
		if(xAxis.lengthSquared() < Mathf.EPS)
			xAxis = vec3f(0f, c, -b)
		yAxis = yAxis.cross(xAxis)
		val x = dot(xAxis)
		val y = dot(yAxis)

		return vec2f(x, y)
	}

	override fun magnitude(length: Number): AbstractVector3f {
		if(lengthSquared() < Mathf.EPS)
			return this
		return normalize(length)
	}

	override fun normalize(length: Number): AbstractVector3f {
		return this * (length.toFloat() / length())
	}

	override fun plus(other: Float): AbstractVector3f {
		return super.plus(other) as AbstractVector3f
	}

	override fun div(other: Float): AbstractVector3f {
		return super.div(other) as AbstractVector3f
	}

	override fun times(other: Float): AbstractVector3f {
		return super.times(other) as AbstractVector3f
	}

	override fun minus(other: Float): AbstractVector3f {
		return super.minus(other) as AbstractVector3f
	}

	override fun plus(other: Number): AbstractVector3f {
		return super.plus(other) as AbstractVector3f
	}

	override fun div(other: Number): AbstractVector3f {
		return super.div(other) as AbstractVector3f
	}

	override fun times(other: Number): AbstractVector3f {
		return super.times(other) as AbstractVector3f
	}

	override fun minus(other: Number): AbstractVector3f {
		return super.minus(other) as AbstractVector3f
	}

	override fun plus(other: AbstractVector<out Float>): AbstractVector3f {
		return plus(vec3f(other))
	}

	override fun times(other: AbstractVector<out Float>): AbstractVector3f {
		return times(vec3f(other))
	}

	override fun div(other: AbstractVector<out Float>): AbstractVector3f {
		return div(vec3f(other))
	}

	override fun minus(other: AbstractVector<out Float>): AbstractVector3f {
		return minus(vec3f(other))
	}

	fun plus(other: AbstractVector3f): AbstractVector3f {
		return vec3f(x + other.x, y + other.y, z + other.z)
	}

	fun minus(other: AbstractVector3f): AbstractVector3f {
		return vec3f(x - other.x, y - other.y, z - other.z)
	}

	fun times(other: AbstractVector3f): AbstractVector3f {
		return vec3f(x * other.x, y * other.y, z * other.z)
	}

	fun div(other: AbstractVector3f): AbstractVector3f {
		return vec3f(x / other.x, y / other.y, z / other.z)
	}

	override fun unaryPlus(): AbstractVector3f {
		return vec3f(this)
	}

	override fun unaryMinus(): AbstractVector3f {
		return vec3f(-x, -y, -z)
	}

	companion object {

		val X: AbstractVector3f = FinalVector3f(1f, 0f, 0f)
		val Y: AbstractVector3f = FinalVector3f(0f, 1f, 0f)
		val Z: AbstractVector3f = FinalVector3f(0f, 0f, 1f)
	}
}

fun vec3f(other: AbstractVector<out Float>): AbstractVector3f {
	require(other.size == 3) { "the size of the vectors are different $other.size != 3" }
	return vec3f(other[0], other[1], other[2])
}

fun vec3f(x: Float, y: Float, z: Float): AbstractVector3f {
	return FinalVector3f(x, y, z)
}

fun org.joml.Vector3f.toMath(): AbstractVector3f {
	return vec3f(x, y, z)
}

fun Vector4f.vec3(): AbstractVector3f {
	return vec3f(x, y, z)
}