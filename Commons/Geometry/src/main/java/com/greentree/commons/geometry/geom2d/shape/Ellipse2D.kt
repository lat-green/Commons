package com.greentree.commons.geometry.geom2d.shape

import com.greentree.commons.geometry.geom2d.util.VectorGeometryUtil.*
import com.greentree.commons.math.vector.AbstractVector2f

data class Ellipse2D(
	val focus1: AbstractVector2f,
	val focus2: AbstractVector2f,
	val radius: Float,
) : FiniteShape2D {

	override val points = calculatePoints(focus1, focus2, radius)
	override val pointsCount: Int
		get() = POINT_IN_ELLIPSE

	override fun isInside(point: AbstractVector2f): Boolean {
		return ((point - focus1) + (point - focus2)).lengthSquared() <= radius * radius
	}

	val center
		get() = (focus1 + focus2) / 2
	private val c
		get() = (focus2 - focus1).length() / 2

	companion object {

		private const val POINT_IN_ELLIPSE = POINT_IN_CIRCLE

		fun calculatePoints(
			focus1: AbstractVector2f,
			focus2: AbstractVector2f,
			radius: Float,
			countPoints: Int = POINT_IN_ELLIPSE,
		): Array<AbstractVector2f> {
			TODO()
		}
	}
}
