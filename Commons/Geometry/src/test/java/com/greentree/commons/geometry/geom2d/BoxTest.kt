package com.greentree.commons.geometry.geom2d

import com.greentree.commons.geometry.TestUtil
import com.greentree.commons.geometry.geom2d.atomic.Box
import com.greentree.commons.geometry.geom2d.atomic.nearestPoint
import com.greentree.commons.math.vector.AbstractVector2f
import com.greentree.commons.math.vector.Vector2f
import org.joml.Matrix3f
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class BoxTest {

	@MethodSource("min_point")
	@ParameterizedTest
	fun nearestPoint(p: Pair<AbstractVector2f, AbstractVector2f>) {
		TestUtil.assertVecEquals(b.nearestPoint(p.first), p.second)
	}

	@MethodSource("model_min_point")
	@ParameterizedTest
	fun model_nearestPoint(p: Pair<Pair<Matrix3f, AbstractVector2f>, AbstractVector2f>) {
		TestUtil.assertVecEquals(b.nearestPoint(p.first.first, p.first.second), p.second)
	}

	companion object {

		private val b = Box
		@JvmStatic
		fun min_point(): Stream<Pair<AbstractVector2f, AbstractVector2f>> {
			val s = ArrayList<Pair<AbstractVector2f, AbstractVector2f>>()
			s.add(Pair(vec(0.0, 1.0), vec(0.0, 1.0)))
			s.add(Pair(vec(0.0, 2.0), vec(0.0, 1.0)))
			s.add(Pair(vec(2.0, .75), vec(1.0, .75)))
			val res = ArrayList<Pair<AbstractVector2f, AbstractVector2f>>()
			for(p in s) {
				res.add(
					Pair(
						vec(p.first.x().toDouble(), p.first.y().toDouble()),
						vec(p.second.x().toDouble(), p.second.y().toDouble())
					)
				)
				res.add(
					Pair(
						vec(-p.first.x().toDouble(), p.first.y().toDouble()),
						vec(-p.second.x().toDouble(), p.second.y().toDouble())
					)
				)
				res.add(
					Pair(
						vec(p.first.x().toDouble(), -p.first.y().toDouble()),
						vec(p.second.x().toDouble(), -p.second.y().toDouble())
					)
				)
				res.add(
					Pair(
						vec(-p.first.x().toDouble(), -p.first.y().toDouble()),
						vec(-p.second.x().toDouble(), -p.second.y().toDouble())
					)
				)
				res.add(
					Pair(
						vec(p.first.y().toDouble(), p.first.x().toDouble()),
						vec(p.second.y().toDouble(), p.second.x().toDouble())
					)
				)
				res.add(
					Pair(
						vec(-p.first.y().toDouble(), p.first.x().toDouble()),
						vec(-p.second.y().toDouble(), p.second.x().toDouble())
					)
				)
				res.add(
					Pair(
						vec(p.first.y().toDouble(), -p.first.x().toDouble()),
						vec(p.second.y().toDouble(), -p.second.x().toDouble())
					)
				)
				res.add(
					Pair(
						vec(-p.first.y().toDouble(), -p.first.x().toDouble()),
						vec(-p.second.y().toDouble(), -p.second.x().toDouble())
					)
				)
			}
			return res.stream()
		}

		private fun vec(x: Double, y: Double): Vector2f {
			return Vector2f(x.toFloat(), y.toFloat())
		}

		@JvmStatic
		fun model_min_point(): Stream<Pair<Pair<Matrix3f, AbstractVector2f>, AbstractVector2f>> {
			val res = ArrayList<Pair<Pair<Matrix3f, AbstractVector2f>, AbstractVector2f>>()
			res.add(
				Pair(
					Pair(Matrix3f(10f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 1f), vec(0.0, 10.0)),
					vec(0.0, 1.0)
				)
			)
			return res.stream()
		}
	}
}