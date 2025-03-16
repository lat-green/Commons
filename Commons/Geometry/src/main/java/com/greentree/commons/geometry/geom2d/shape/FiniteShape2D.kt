package com.greentree.commons.geometry.geom2d.shape

import com.greentree.commons.geometry.geom2d.Shape2D
import com.greentree.commons.geometry.geom2d.Shape2DUtil
import com.greentree.commons.geometry.geom2d.aabb
import com.greentree.commons.geometry.geom2d.util.boundingBox
import com.greentree.commons.geometry.geom2d.util.boundingCircle
import com.greentree.commons.math.MathLine1D
import com.greentree.commons.math.projection
import com.greentree.commons.math.x
import com.greentree.commons.math.y
import org.joml.Vector2f
import org.joml.Vector2fc

interface FiniteShape2D : Shape2D {

	val points: Array<Vector2fc>
	val pointsCount: Int
		get() = points.size
	val linesLoop: Array<Line2D>
		get() = Shape2DUtil.toLineLoop(points)
	val linesStrip: Array<Line2D>
		get() = Shape2DUtil.toLineStrip(points)

	fun nearestLine(point: Vector2fc): Line2D {
		return linesLoop.minBy { it.distanceSquared(point) }
	}

	override fun nearestPoint(point: Vector2fc): Vector2fc {
		lateinit var res: Vector2fc
		var mp: Vector2fc
		var dis: Float
		var dis0 = Float.MAX_VALUE
		for(line in linesLoop) {
			mp = line.nearestPoint(point)
			dis = mp.distanceSquared(point)
			if(dis < dis0) {
				dis0 = dis
				res = mp
			}
		}
		return res
	}

	override fun projection(normal: Vector2fc): MathLine1D {
		return MathLine1D(*points.map { it.projection(normal) }
			.toFloatArray())
	}

	override val projectionX
		get() = MathLine1D(*points.map { it.x }.toFloatArray())
	override val projectionY
		get() = MathLine1D(*points.map { it.y }.toFloatArray())

	fun isInside(x: Float, y: Float): Boolean {
		return isInside(Vector2f(x, y))
	}

	override fun isInside(point: Vector2fc): Boolean {
		if(!aabb.isInside(point)) return false
		val points = Shape2DUtil.cycle(points, 1)
		for(i in 0 until points.size - 1) if(0 < Shape2DUtil.getSin(
				points[i],
				points[i + 1],
				point
			)
		) return false
		return true
	}

	override val boundingBox
		get() = boundingBox(points)
	override val boundingCircle
		get() = boundingCircle(points)

	fun triangulation(): Collection<Triangle2D> {
		return Shape2DUtil.triangulation(*points)
	}

	val normals: Collection<Vector2fc>
		get() {
			val res: MutableCollection<Vector2fc> = ArrayList()
			for(l in linesLoop) res.add(l.normal)
			return res
		}
}