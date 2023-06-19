package com.greentree.commons.geometry.geom2d

import com.greentree.commons.math.vector.AbstractVector2f

data class AABB(
	val minX: Float,
	val maxX: Float,
	val minY: Float,
	val maxY: Float,
) {

	fun isIntersect(other: AABB) =
		maxX >= other.minX && minX <= other.maxX && maxY >= other.minY && minY <= other.maxY

	operator fun times(scale: AbstractVector2f): AABB {
		val cX = (maxX + minX) / 2
		val cY = (maxY + minY) / 2
		val sX = (maxX - minX) / 2 * scale.x
		val sY = (maxY - minY) / 2 * scale.y
		return AABB(cX - sX, cX + sX, cY - sY, cY + sY)
	}

	operator fun plus(move: AbstractVector2f): AABB {
		return AABB(minX + move.x, maxX + move.x, minY + move.y, maxY + move.y)
	}

	val width
		get() = maxX - minX
	val height
		get() = maxY - minY
}
