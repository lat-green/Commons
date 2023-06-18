package com.greentree.commons.geometry.geom2d

import com.greentree.commons.math.MathLine1D
import com.greentree.commons.math.vector.AbstractVector2f
import com.greentree.commons.math.vector.vec2f

object Circle : Shape2D {

	override val center = vec2f(0f, 0f)
	override val radius = 1f

	override fun nearestPoint(point: AbstractVector2f): AbstractVector2f {
		if(point.isZero())
			return vec2f(1f, 0f)
		return point.normalize()
	}

	override fun aabb(): AABB {
		return AABB(-1f, 1f, -1f, 1f)
	}

	override fun isInside(point: AbstractVector2f) = point.lengthSquared() <= 1f

	override fun projection(normal: AbstractVector2f): MathLine1D {
		return MathLine1D(-1f, 1f)
	}

	@JvmStatic
	fun create(x: Float, y: Float, radius: Float): Shape2D {
		return Circle.moveTo(x, y).scale(radius)
	}

	@JvmStatic
	fun create(center: AbstractVector2f, radius: Float): Shape2D {
		return Circle.moveTo(center).scale(radius)
	}

	override fun toString() = "Circle"
}

private fun AbstractVector2f.isZero() = x == 0f && y == 0f
