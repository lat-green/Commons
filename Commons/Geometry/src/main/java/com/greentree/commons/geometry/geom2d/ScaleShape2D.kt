package com.greentree.commons.geometry.geom2d

import com.greentree.commons.math.MathLine1D
import com.greentree.commons.math.Mathf
import com.greentree.commons.math.vector.AbstractVector2f
import org.joml.Matrix2f
import java.lang.Float.*

class ScaleShape2D(
	private val shape: Shape2D,
	private val scale: AbstractVector2f,
) : Shape2D {

	override val center: AbstractVector2f
		get() = shape.center
	override val radius: Float
		get() = max(scale.x, scale.y) * shape.radius

	override fun nearestPoint(point: AbstractVector2f): AbstractVector2f {
		return shape.nearestPoint(point / scale) * scale
	}

	override fun aabb(): AABB {
		return shape.aabb() * scale
	}

	override fun isInside(point: AbstractVector2f): Boolean {
		return shape.isInside(point / scale)
	}

	override fun projection(normal: AbstractVector2f): MathLine1D {
		val n = (normal * scale).normalize()
		val p = shape.projection(n)
		val r = (n * Matrix2f().rotate(Mathf.PI / 2)).dot(scale)
		return p * r
	}

	override fun toString() = "Scaled $shape $scale"
}

private operator fun MathLine1D.times(scale: Float): MathLine1D {
	if(scale < 0)
		return MathLine1D(max * scale, min * scale)
	return MathLine1D(min * scale, max * scale)
}
