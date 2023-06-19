package com.greentree.commons.geometry.geom2d

import com.greentree.commons.math.MathLine1D
import com.greentree.commons.math.vector.AbstractVector2f
import com.greentree.commons.math.vector.vec2f

data class Circle(
	override val center: AbstractVector2f,
	override val radius: Float = 1f,
) : Shape2D {

	constructor(
		x: Float = 0f,
		y: Float = 0f,
		radius: Float = 1f,
	) : this(vec2f(x, y), radius)

	override fun nearestPoint(point: AbstractVector2f): AbstractVector2f {
		if(point.isZero())
			return vec2f(1f, 0f)
		return point.normalize()
	}

	override fun aabb(): AABB {
		return AABB(-1f, 1f, -1f, 1f)
	}

	override fun isInside(point: AbstractVector2f) = point.lengthSquared() <= 1f

	override fun moveTo(point: AbstractVector2f): Shape2D {
		return Circle(point, radius)
	}

	override fun scale(scale: AbstractVector2f): Shape2D {
		return Oval(center, scale.x * radius, scale.y * radius)
	}

	override fun projection(normal: AbstractVector2f): MathLine1D {
		return MathLine1D(-radius, radius)
	}

	override fun toString() = "Circle"
}

private fun AbstractVector2f.isZero() = x == 0f && y == 0f
