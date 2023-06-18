package com.greentree.commons.geometry.geom2d

import com.greentree.commons.math.MathLine1D
import com.greentree.commons.math.Mathf
import com.greentree.commons.math.vector.AbstractVector2f
import com.greentree.commons.math.vector.vec2f

object Square : Shape2D {

	private val NORMALS: Iterable<AbstractVector2f> = listOf(
		vec2f(1f, 0f), vec2f(-1f, 0f), vec2f(0f, 1f),
		vec2f(0f, -1f)
	)
	private val POINTS: Iterable<AbstractVector2f> = listOf(
		vec2f(1f, 1f), vec2f(-1f, 1f), vec2f(1f, -1f),
		vec2f(-1f, -1f)
	)
	override val center = vec2f(0f, 0f)
	override val radius: Float
		get() = TODO("Not yet implemented")

	override fun nearestPoint(point: AbstractVector2f): AbstractVector2f {
		var tx = (point.x() + 1) / 2
		var ty = (point.y() + 1) / 2
		tx = Mathf.clamp(0f, 1f, tx)
		ty = Mathf.clamp(0f, 1f, ty)
		return vec2f(2 * tx - 1, 2 * ty - 1)
	}

	override fun aabb(): AABB {
		return AABB(-1f, 1f, -1f, 1f)
	}

	override fun isInside(point: AbstractVector2f): Boolean {
		if(point.x() > 1)
			return false
		if(point.x() < -1)
			return false
		if(point.y() > 1)
			return false
		if(point.y() < -1)
			return false
		return true
	}

	override fun projection(normal: AbstractVector2f): MathLine1D {
		var min = Float.MAX_VALUE
		var max = Float.MIN_VALUE
		for(p in POINTS) {
			val v = p.getProjectionPoint(normal)
			if(max < v) max = v
			if(min > v) min = v
		}
		return MathLine1D(min, max)
	}
}

private fun AbstractVector2f.isZero() = x == 0f && y == 0f