package com.greentree.commons.geometry.geom2d.shape

import com.greentree.commons.math.vector.AbstractVector2f
import com.greentree.commons.math.vector.vec2

data class Triangle2D(
	val p1: AbstractVector2f,
	val p2: AbstractVector2f,
	val p3: AbstractVector2f,
) : FiniteShape2D {

	override val points = arrayOf(p1, p2, p3)

	constructor(
		x1: Float, y1: Float,
		x2: Float, y2: Float,
		x3: Float, y3: Float,
	) : this(
		vec2(x1, y1),
		vec2(x2, y2),
		vec2(x3, y3)
	)
}
