package com.greentree.commons.geometry.geom2d.operation

import com.greentree.commons.geometry.geom2d.Shape2D
import com.greentree.commons.geometry.geom2d.Shape2DUtil
import com.greentree.commons.geometry.geom2d.collision.CollisionEvent2D
import com.greentree.commons.geometry.geom2d.isIntersect
import com.greentree.commons.geometry.geom2d.shape.FiniteShape2D
import com.greentree.commons.math.MathLine2D
import org.joml.Vector2f
import org.joml.Vector2fc

open class Shape2DBinaryOperation<A : Shape2D, B : Shape2D> {

	open fun getCollisionEvent(a: A, b: B): CollisionEvent2D.Builder {
		a as FiniteShape2D
		b as FiniteShape2D
		val normal = Shape2DUtil.getCollisionNormalOnNormalProjection(a, b)
		return CollisionEvent2D.Builder(
			Shape2DUtil.getCollisionPoint(a, b), normal,
			Shape2DUtil.getProjectionOverlay(a, b, Vector2f(normal.y(), -normal.x()))
		)
	}

	open fun getContactPoint(a: A, b: B): Collection<Vector2fc> {
		a as FiniteShape2D
		b as FiniteShape2D
		val res: MutableSet<Vector2fc> = HashSet()
		for(al in a.linesLoop)
			for(bl in b.linesLoop) {
				val c = MathLine2D.contact(al.mathLine, bl.mathLine)
				if(c == null || !al.boundingBox.isInside(c.x(), c.y()) || !bl.boundingBox.isInside(
						c.x(),
						c.y()
					)
				) continue
				res.add(c)
			}
		return res
	}

	open fun isIntersect(a: A, b: B): Boolean {
		a as FiniteShape2D
		b as FiniteShape2D
		if(!a.boundingBox.isIntersect(b.boundingBox))
			return false
		return Shape2DUtil.isIntersectOnNormalProjection(a, b)
	}

	companion object {

		@JvmField
		val DEFAULT = Shape2DBinaryOperation<Shape2D, Shape2D>()
	}
}
