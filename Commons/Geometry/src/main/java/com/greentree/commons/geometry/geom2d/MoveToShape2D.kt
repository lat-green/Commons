package com.greentree.commons.geometry.geom2d

import com.greentree.commons.math.MathLine1D
import com.greentree.commons.math.vector.AbstractVector2f

class MoveToShape2D(
	private val shape: Shape2D,
	private val point: AbstractVector2f,
) : Shape2D {

	private inline val move
		get() = point - shape.center
	override val center: AbstractVector2f
		get() = point
	override val radius: Float
		get() = shape.radius

	override fun nearestPoint(point: AbstractVector2f): AbstractVector2f {
		return shape.nearestPoint(point - move) + move
	}

	override fun moveTo(point: AbstractVector2f): Shape2D {
		return MoveToShape2D(shape, point)
	}

	override fun projection(normal: AbstractVector2f): MathLine1D {
		TODO("Not yet implemented")
	}

	override fun aabb(): AABB {
		return shape.aabb() + move
	}

	override fun isInside(point: AbstractVector2f): Boolean {
		return shape.isInside(point - move)
	}
}