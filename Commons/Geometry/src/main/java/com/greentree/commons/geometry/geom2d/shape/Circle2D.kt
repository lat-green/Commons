package com.greentree.commons.geometry.geom2d.shape

import com.greentree.commons.geometry.geom2d.Shape2D
import com.greentree.commons.math.MathLine1D
import com.greentree.commons.math.projection
import com.greentree.commons.math.vec2f
import org.joml.Vector2fc
import org.joml.minus
import org.joml.plus

data class Circle2D
@JvmOverloads
constructor(
	val center: Vector2fc,
	val radius: Float = 1f,
) : Shape2D {

	@JvmOverloads
	constructor(x: Float, y: Float, radius: Float = 1f) : this(vec2f(x, y), radius)

	@JvmOverloads
	constructor() : this(vec2f(0f, 0f), 1f)

	override fun distance(point: Vector2fc): Float {
		return (point - center).length() - radius
	}

	override fun isInside(point: Vector2fc): Boolean {
		return (point - center).lengthSquared() <= radius * radius
	}

	override fun nearestPoint(point: Vector2fc): Vector2fc {
		return center + (point - center).normalize(radius)
	}

	override fun projection(normal: Vector2fc): MathLine1D {
		val projection = center.projection(normal)
		return MathLine1D(
			projection + radius,
			projection - radius
		)
	}

	override val boundingBox: Rectangle2D
		get() {
			val r = vec2f(radius, radius)
			return Rectangle2D(center - r, center + r)
		}
	override val boundingCircle: Circle2D
		get() = this
}
