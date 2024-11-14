package com.greentree.commons.geometry.geom2d.shape

import com.greentree.commons.math.vector.AbstractVector2f
import com.greentree.commons.math.vector.vec2
import com.greentree.commons.math.vector.vec2f

data class Rectangle2D(
	val p1: AbstractVector2f,
	val p2: AbstractVector2f,
) : FiniteShape2D {

	constructor(
		x1: Float, y1: Float,
		x2: Float, y2: Float,
	) : this(vec2(x1, y1), vec2(x2, y2))

	override val width: Float
		get() = p2.x - p1.x
	override val height: Float
		get() = p2.y - p1.y
	override val minX
		get() = p1.x
	override val maxX
		get() = p2.x
	override val minY
		get() = p1.y
	override val maxY
		get() = p2.y

	init {
		require(p1.x <= p2.x)
		require(p1.y <= p2.y)
	}

	override fun isInside(x: Float, y: Float): Boolean {
		return x in minX .. maxX && y in minY .. maxY
	}

	override val boundingBox
		get() = this
	override val points: Array<AbstractVector2f>
		get() = arrayOf(p1, vec2f(p1.x, p2.y), p2, vec2f(p2.x, p1.y))
}
