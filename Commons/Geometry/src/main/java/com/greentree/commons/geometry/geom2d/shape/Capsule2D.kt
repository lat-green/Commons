package com.greentree.commons.geometry.geom2d.shape

import com.greentree.commons.geometry.geom2d.Shape2DUtil.POINT_IN_CIRCLE
import com.greentree.commons.math.vec2f
import org.joml.Matrix2f
import org.joml.Vector2fc
import org.joml.minus
import org.joml.plus
import org.joml.times
import kotlin.math.floor

data class Capsule2D(
	val focus1: Vector2fc,
	val focus2: Vector2fc,
	val radius: Float,
) : FiniteShape2D {

	override val points = calculatePoints(focus1, focus2, radius)
	override val pointsCount: Int
		get() = POINT_IN_CAPSULE

	companion object {

		private const val POINT_IN_CAPSULE = POINT_IN_CIRCLE

		fun calculatePoints(
			focus1: Vector2fc,
			focus2: Vector2fc,
			radius: Float,
			countPoints: Int = POINT_IN_CAPSULE,
		): Array<Vector2fc> {
			val init_focus_vec = focus2 - focus1 // 1 -> 2
			if(init_focus_vec.length() == 0f) init_focus_vec.set(1f, 0f)
			var focus_vec = vec2f(init_focus_vec)
			focus_vec = focus_vec.times(Matrix2f().rotate((Math.PI / 2).toFloat())).normalize(radius)
			val points = arrayOfNulls<Vector2fc>(countPoints)
			val mat = Matrix2f().rotate((2 * Math.PI / countPoints).toFloat())
			for(i in floor((countPoints / 2f).toDouble()).toInt() until countPoints) {
				focus_vec = focus_vec.times(mat)
				points[i] = focus1.plus(focus_vec)
			}
			var i = 0
			while(i < floor((POINT_IN_CIRCLE / 2f).toDouble())) {
				focus_vec = focus_vec.times(mat)
				points[i] = focus2.plus(focus_vec)
				i++
			}
			return points as Array<Vector2fc>
		}
	}
}
