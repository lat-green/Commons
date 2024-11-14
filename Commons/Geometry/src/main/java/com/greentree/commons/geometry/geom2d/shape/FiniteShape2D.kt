package com.greentree.commons.geometry.geom2d.shape

import com.greentree.commons.geometry.geom2d.Shape2D
import com.greentree.commons.geometry.geom2d.Shape2DUtil
import com.greentree.commons.geometry.geom2d.aabb
import com.greentree.commons.geometry.geom2d.util.VectorGeometryUtil
import com.greentree.commons.geometry.geom2d.util.boundingBox
import com.greentree.commons.geometry.geom2d.util.boundingCircle
import com.greentree.commons.math.MathLine1D
import com.greentree.commons.math.vector.AbstractVector2f
import com.greentree.commons.math.vector.Vector2f

interface FiniteShape2D : Shape2D {

	val points: Array<AbstractVector2f>
	val pointsCount: Int
		get() = points.size
	val linesLoop: Array<Line2D>
		get() = Shape2DUtil.toLineLoop(points)
	val linesStrip: Array<Line2D>
		get() = Shape2DUtil.toLineStrip(points)

	fun nearestLine(point: AbstractVector2f): Line2D {
		return linesLoop.minBy { it.distanceSquared(point) }
	}

	override fun nearestPoint(point: AbstractVector2f): AbstractVector2f {
		lateinit var res: AbstractVector2f
		var mp: AbstractVector2f
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

	override fun projection(normal: AbstractVector2f): MathLine1D {
		return MathLine1D(*points.map { it.projection(normal) }.toFloatArray())
	}

	override val projectionX
		get() = MathLine1D(*points.map { it.x }.toFloatArray())
	override val projectionY
		get() = MathLine1D(*points.map { it.y }.toFloatArray())

	fun isInside(x: Float, y: Float): Boolean {
		return isInside(Vector2f(x, y))
	}

	override fun isInside(point: AbstractVector2f): Boolean {
		if(!aabb.isInside(point)) return false
		val points = VectorGeometryUtil.cycle(points, 1)
		for(i in 0 until points.size - 1) if(0 < VectorGeometryUtil.getSin(
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
		return VectorGeometryUtil.triangulation(*points)
	}

	val normals: Collection<AbstractVector2f>
		get() {
			val res: MutableCollection<AbstractVector2f> = ArrayList()
			for(l in linesLoop) res.add(l.normal)
			return res
		}
}