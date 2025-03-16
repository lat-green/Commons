package com.greentree.commons.geometry.geom2d.shape

import com.greentree.commons.math.MathLine2D
import com.greentree.commons.math.Mathf.sqrt
import com.greentree.commons.math.vec2f
import org.joml.Vector2fc

data class Line2D(
	val p1: Vector2fc,
	val p2: Vector2fc,
) : FiniteShape2D {

	constructor(x1: Float, y1: Float, x2: Float, y2: Float) : this(vec2f(x1, y1), vec2f(x2, y2))

	override val points = arrayOf(p1, p2)

	override fun nearestLine(point: Vector2fc) = this

	override fun nearestPoint(point: Vector2fc): Vector2fc {
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
	val normal: Vector2fc
		get() = mathLine.normal
	override val normals: List<Vector2fc>
		get() = listOf(normal)
	val length: Float
		get() = sqrt(lengthSquared)
	val lengthSquared: Float
		get() = p1.distanceSquared(p2)
}
