package com.greentree.commons.geometry.geom2d.operation

import com.greentree.commons.geometry.geom2d.collision.CollisionEvent2D
import com.greentree.commons.geometry.geom2d.shape.Circle2D
import org.joml.Vector2f
import org.joml.minus

class CircleVsCircle : Shape2DBinaryOperation<Circle2D, Circle2D>() {

	override fun getCollisionEvent(a: Circle2D, b: Circle2D): CollisionEvent2D.Builder {
		val n: Vector2f = b.center - a.center
		val penetration = -n.length() + a.radius + b.radius
		val x1 = a.center.x()
		val y1 = a.center.y()
		val r1 = a.radius
		val x2 = b.center.x() - x1
		val y2 = b.center.y() - y1
		val r2 = b.radius
		val al = -2 * x2
		val bl = -2 * y2
		val cl = x2 * x2 + y2 * y2 + r1 * r1 - r2 * r2
		val ab = al * al + bl * bl
		val res = CollisionEvent2D.Builder(
			Vector2f(-al * cl / ab + x1, -bl * cl / ab + y1),
			n.normalize(1f), penetration
		)
		return res
	}

	override fun isIntersect(a: Circle2D, b: Circle2D): Boolean {
		val r = a.radius + b.radius
		return b.center.distanceSquared(a.center) <= r * r
	}
}
