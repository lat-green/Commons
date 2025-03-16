package com.greentree.commons.geometry.geom2d

import com.greentree.commons.geometry.geom2d.shape.Circle2D
import com.greentree.commons.geometry.geom2d.shape.Rectangle2D
import com.greentree.commons.math.MathLine1D
import com.greentree.commons.math.Mathf
import com.greentree.commons.math.vec2f
import org.joml.Vector2fc

interface Shape2D {

	fun distance(point: Vector2fc): Float {
		return Mathf.sqrt(distanceSquared(point))
	}

	fun distanceSquared(point: Vector2fc): Float {
		return point.distanceSquared(nearestPoint(point))
	}

	fun nearestPoint(point: Vector2fc): Vector2fc

	fun projection(normal: Vector2fc): MathLine1D
	val projectionX
		get() = projection(vec2f(0f, 1f))
	val projectionY
		get() = projection(vec2f(1f, 0f))

	fun isInside(point: Vector2fc): Boolean

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