package com.greentree.commons.geometry.geom2d

import com.greentree.commons.math.MathLine1D
import com.greentree.commons.math.vector.AbstractVector2f
import com.greentree.commons.math.vector.vec2f

interface Shape2D {

	val center: AbstractVector2f
	val x
		get() = center.x
	val y
		get() = center.y
	val width
		get() = aabb().width
	val height
		get() = aabb().height
	val radius: Float

	fun nearestPoint(point: AbstractVector2f): AbstractVector2f

	fun aabb(): AABB

	fun isInside(point: AbstractVector2f): Boolean

	fun moveTo(point: AbstractVector2f): Shape2D

	fun scale(scale: AbstractVector2f): Shape2D

	fun moveTo(x: Float, y: Float): Shape2D {
		return moveTo(vec2f(x, y))
	}

	fun scale(xy: Float): Shape2D {
		if(xy == 1f)
			return this
		return scale(xy, xy)
	}

	fun scale(x: Float, y: Float): Shape2D {
		return scale(vec2f(x, y))
	}

	fun projection(normal: AbstractVector2f): MathLine1D
}

inline infix fun AbstractVector2f.inside(shape: Shape2D) = shape.isInside(this)