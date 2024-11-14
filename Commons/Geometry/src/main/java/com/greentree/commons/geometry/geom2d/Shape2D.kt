package com.greentree.commons.geometry.geom2d

import com.greentree.commons.geometry.geom2d.shape.Circle2D
import com.greentree.commons.geometry.geom2d.shape.Rectangle2D
import com.greentree.commons.math.MathLine1D
import com.greentree.commons.math.Mathf
import com.greentree.commons.math.vector.AbstractVector2f
import com.greentree.commons.math.vector.vec2

interface Shape2D {

	fun distance(point: AbstractVector2f): Float {
		return Mathf.sqrt(distanceSquared(point))
	}

	fun distanceSquared(point: AbstractVector2f): Float {
		return point.distanceSquared(nearestPoint(point))
	}

	fun nearestPoint(point: AbstractVector2f): AbstractVector2f

	fun projection(normal: AbstractVector2f): MathLine1D
	val projectionX
		get() = projection(vec2(0f, 1f))
	val projectionY
		get() = projection(vec2(1f, 0f))

	fun isInside(point: AbstractVector2f): Boolean

	val boundingBox: Rectangle2D
	val boundingCircle: Circle2D

	val width: Float
		get() = maxX - minX
	val height: Float
		get() = maxY - minY
	val minX: Float
		get() = projectionX.min
	val maxX: Float
		get() = projectionX.max
	val minY: Float
		get() = projectionY.min
	val maxY: Float
		get() = projectionY.max
}

fun Shape2D.isIntersect(other: Shape2D): Boolean {
	return Shape2DUtil.isIntersect(this, other)
}

val Shape2D.aabb
	get() = boundingBox