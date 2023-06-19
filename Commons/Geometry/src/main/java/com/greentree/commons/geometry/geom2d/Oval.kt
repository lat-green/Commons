package com.greentree.commons.geometry.geom2d

import com.greentree.commons.math.MathLine1D
import com.greentree.commons.math.vector.AbstractVector2f

data class Oval(
	override val center: AbstractVector2f,
	override val width: Float = 1f,
	override val height: Float = 1f,
) : Shape2D {

	override val radius: Float
		get() = TODO("Not yet implemented")

	override fun nearestPoint(point: AbstractVector2f): AbstractVector2f {
		TODO("Not yet implemented")
	}

	override fun aabb(): AABB {
		TODO("Not yet implemented")
	}

	override fun isInside(point: AbstractVector2f): Boolean {
		TODO("Not yet implemented")
	}

	override fun moveTo(point: AbstractVector2f): Shape2D {
		TODO("Not yet implemented")
	}

	override fun scale(scale: AbstractVector2f): Shape2D {
		TODO("Not yet implemented")
	}

	override fun projection(normal: AbstractVector2f): MathLine1D {
		TODO("Not yet implemented")
	}
}