package com.greentree.commons.geometry.geom2d

import com.greentree.commons.math.vector.AbstractVector2f
import com.greentree.commons.math.vector.minus
import kotlin.math.max
import kotlin.math.min

/** @author Arseny Latyshev
 */
class AABB {

	val minX: Float
	val maxX: Float
	val minY: Float
	val maxY: Float

	constructor(vararg points: AbstractVector2f) {
		var maxx = -Float.MAX_VALUE
		var maxy = -Float.MAX_VALUE
		var minx = Float.MAX_VALUE
		var miny = Float.MAX_VALUE
		for(p in points) {
			maxx = max(maxx, p.x)
			maxy = max(maxy, p.y)
			minx = min(minx, p.x)
			miny = min(miny, p.y)
		}
		val sX = (maxx - minx) * BORDER_COEFFICIENT / 2
		minx += sX
		maxx -= sX
		val sY = (maxy - miny) * BORDER_COEFFICIENT / 2
		miny += sY
		maxy -= sY
		minX = minx
		maxX = maxx
		minY = miny
		maxY = maxy
	}

	constructor(
		minX: Float,
		maxX: Float,
		minY: Float,
		maxY: Float,
	) {
		var minx = minX
		var maxx = maxX
		var miny = minY
		var maxy = maxY
		val sX = (maxx - minx) * BORDER_COEFFICIENT / 2
		minx += sX
		maxx -= sX
		val sY = (maxy - miny) * BORDER_COEFFICIENT / 2
		miny += sY
		maxy -= sY
		this.minX = minx
		this.maxX = maxx
		this.minY = miny
		this.maxY = maxy
	}

	val height: Float
		get() = maxY - minY
	val centerX: Float
		get() = (maxX + minX) / 2f
	val centerY: Float
		get() = (maxY + minY) / 2f
	val width: Float
		get() = maxX - minX

	fun isIntersect(other: AABB): Boolean {
		return maxX >= other.minX && minX <= other.maxX && maxY >= other.minY && minY <= other.maxY
	}

	fun isInside(x: Float, y: Float): Boolean {
		return minX <= x && x <= maxX && minY <= y && y <= maxY
	}

	override fun toString(): String {
		return "AABB(minX=$minX, maxX=$maxX, minY=$minY, maxY=$maxY)"
	}

	companion object {

		private const val BORDER_COEFFICIENT = 0.000001f
	}
}