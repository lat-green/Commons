package com.greentree.commons.geometry.geom2d.shape

import com.greentree.commons.math.MathLine2D
import com.greentree.commons.math.Mathf.Companion.sqrt
import com.greentree.commons.math.vector.AbstractVector2f
import com.greentree.commons.math.vector.vec2f

data class Line2D(
	val p1: AbstractVector2f,
	val p2: AbstractVector2f,
) : FiniteShape2D {

	constructor(x1: Float, y1: Float, x2: Float, y2: Float) : this(vec2f(x1, y1), vec2f(x2, y2))

	override val points = arrayOf(p1, p2)

	override fun nearestLine(point: AbstractVector2f) = this

	override fun nearestPoint(point: AbstractVector2f): AbstractVector2f {
		val mp = mathLine.minPoint(point)
		if(p1.x() < p2.x()) {
			if(p1.x() > mp.x()) return p1
			if(p2.x() < mp.x()) return p2
		}
		if(p1.x() > p2.x()) {
			if(p1.x() < mp.x()) return p1
			if(p2.x() > mp.x()) return p2
		}
		if(p1.y() < p2.y()) {
			if(p1.y() > mp.y()) return p1
			if(p2.y() < mp.y()) return p2
		}
		if(p1.y() > p2.y()) {
			if(p1.y() < mp.y()) return p1
			if(p2.y() > mp.y()) return p2
		}
		return mp
	}

	fun p1() = p1
	fun p2() = p2

	val mathLine
		get() = MathLine2D(p1, p2)
	val normal
		get() = mathLine.normal
	override val normals
		get() = listOf(normal)
	val length
		get() = sqrt(lengthSquared)
	val lengthSquared
		get() = p1.distanceSquared(p2)
}
