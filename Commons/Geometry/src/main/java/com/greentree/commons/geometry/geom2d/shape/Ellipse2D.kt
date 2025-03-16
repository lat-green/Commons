package com.greentree.commons.geometry.geom2d.shape

import com.greentree.commons.geometry.geom2d.Shape2DUtil.POINT_IN_CIRCLE
import org.joml.Vector2fc
import org.joml.minus
import org.joml.plus

data class Ellipse2D(
	val focus1: Vector2fc,
	val focus2: Vector2fc,
	val radius: Float,
) : FiniteShape2D {

	override val points = calculatePoints(focus1, focus2, radius)
	override val pointsCount: Int
		get() = POINT_IN_ELLIPSE

	override fun isInside(point: Vector2fc): Boolean {
		return ((point - focus1) + (point - focus2)).lengthSquared() <= radius * radius
	}

	val center
		get() = (focus1 + focus2) / 2f
	private val c
		get() = (focus2 - focus1).length() / 2

	companion object {

		private const val POINT_IN_ELLIPSE = POINT_IN_CIRCLE

		fun calculatePoints(
			focus1: Vector2fc,
			focus2: Vector2fc,
			radius: Float,
			countPoints: Int = POINT_IN_ELLIPSE,
		): Array<Vector2fc> {
			TODO()
		}
	}
}
