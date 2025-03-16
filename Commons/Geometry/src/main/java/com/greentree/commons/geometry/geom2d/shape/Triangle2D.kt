package com.greentree.commons.geometry.geom2d.shape

import com.greentree.commons.math.vec2f
import org.joml.Vector2fc

data class Triangle2D(
	val p1: Vector2fc,
	val p2: Vector2fc,
	val p3: Vector2fc,
) : FiniteShape2D {

	override val points = arrayOf(p1, p2, p3)

	constructor(
		x1: Float, y1: Float,
		x2: Float, y2: Float,
		x3: Float, y3: Float,
	) : this(
		vec2f(x1, y1),
		vec2f(x2, y2),
		vec2f(x3, y3)
	)
}
