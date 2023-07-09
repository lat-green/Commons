package com.greentree.commons.geometry.geom2d.atomic

import com.greentree.commons.geometry.geom2d.util.VectorGeometryUtil
import com.greentree.commons.math.MathLine1D
import com.greentree.commons.math.Mathf
import com.greentree.commons.math.vector.AbstractVector2f
import com.greentree.commons.math.vector.FinalVector2f
import com.greentree.commons.math.vector.Vector2f

object Box : SimpleShape2D {

	override fun projection(normal: AbstractVector2f): MathLine1D {
		var min = Float.MAX_VALUE
		var max = Float.MIN_VALUE
		for(p in POINTS) {
			val v = VectorGeometryUtil.getProjectionPoint(p, normal)
			if(max < v) max = v
			if(min > v) min = v
		}
		return MathLine1D(min, max)
	}

	override fun isInside(point: AbstractVector2f): Boolean {
		if(point.x() > 1) return false
		if(point.x() < -1) return false
		return if(point.y() > 1) false else point.y() >= -1
	}

	override fun nearestPoint(point: AbstractVector2f): AbstractVector2f {
		var tx = (point.x() + 1) / 2
		var ty = (point.y() + 1) / 2
		tx = Mathf.clamp(0f, 1f, tx)
		ty = Mathf.clamp(0f, 1f, ty)
		return Vector2f(2 * tx - 1, 2 * ty - 1)
	}

	override fun normals(): Iterable<AbstractVector2f> {
		return NORMALS
	}

	override fun points(): Iterable<AbstractVector2f> {
		return POINTS
	}

	private val NORMALS: Iterable<AbstractVector2f> = listOf(
		FinalVector2f(1f, 0f), FinalVector2f(-1f, 0f), FinalVector2f(0f, 1f),
		FinalVector2f(0f, -1f)
	)
	private val POINTS: Iterable<AbstractVector2f> = listOf(
		FinalVector2f(1f, 1f), FinalVector2f(-1f, 1f), FinalVector2f(1f, -1f),
		FinalVector2f(-1f, -1f)
	)
}