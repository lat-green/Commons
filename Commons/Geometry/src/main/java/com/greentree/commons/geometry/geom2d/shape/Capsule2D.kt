package com.greentree.commons.geometry.geom2d.shape

import com.greentree.commons.geometry.geom2d.util.VectorGeometryUtil.*
import com.greentree.commons.math.vector.AbstractVector2f
import com.greentree.commons.math.vector.vec2f
import org.joml.Matrix2f
import kotlin.math.floor

data class Capsule2D(
	val focus1: AbstractVector2f,
	val focus2: AbstractVector2f,
	val radius: Float,
) : FiniteShape2D {

	override val points = calculatePoints(focus1, focus2, radius)
	override val pointsCount: Int
		get() = POINT_IN_CAPSULE

	companion object {

		private const val POINT_IN_CAPSULE = POINT_IN_CIRCLE

		fun calculatePoints(
			focus1: AbstractVector2f,
			focus2: AbstractVector2f,
			radius: Float,
			countPoints: Int = POINT_IN_CAPSULE,
		): Array<AbstractVector2f> {
			val init_focus_vec = focus2.minus(focus1).toMutable() // 1 -> 2
			if(init_focus_vec.length() == 0f) init_focus_vec.set(1f, 0f)
			var focus_vec = vec2f(init_focus_vec)
			focus_vec = focus_vec.times(Matrix2f().rotate((Math.PI / 2).toFloat())).normalize(radius)
			val points = arrayOfNulls<AbstractVector2f>(countPoints)
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
			return points as Array<AbstractVector2f>
		}
	}
}
