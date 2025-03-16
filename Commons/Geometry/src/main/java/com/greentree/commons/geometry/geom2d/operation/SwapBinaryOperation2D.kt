package com.greentree.commons.geometry.geom2d.operation

import com.greentree.commons.geometry.geom2d.Shape2D
import com.greentree.commons.geometry.geom2d.collision.CollisionEvent2D
import org.joml.Vector2fc

data class SwapBinaryOperation2D<A : Shape2D, B : Shape2D>(
	private val origin: Shape2DBinaryOperation<B, A>,
) : Shape2DBinaryOperation<A, B>() {

	override fun getCollisionEvent(a: A, b: B): CollisionEvent2D.Builder {
		val e = origin.getCollisionEvent(b, a)
		return e.inverse()
	}

	override fun getContactPoint(a: A, b: B): Collection<Vector2fc> {
		return origin.getContactPoint(b, a)
	}

	override fun isIntersect(a: A, b: B): Boolean {
		return origin.isIntersect(b, a)
	}

	override fun toString(): String {
		return "SwapBinaryOperation2D [shape2DBinaryOperation=$origin]"
	}
}
