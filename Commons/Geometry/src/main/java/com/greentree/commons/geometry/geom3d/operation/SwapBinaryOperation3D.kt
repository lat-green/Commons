package com.greentree.commons.geometry.geom3d.operation

import com.greentree.commons.geometry.geom3d.IShape3D
import com.greentree.commons.geometry.geom3d.collision.CollisionEvent3D
import org.joml.unaryMinus

data class SwapBinaryOperation3D<A : IShape3D, B : IShape3D>(
	val origin: Shape3DBinaryOperation<B, A>,
) : Shape3DBinaryOperation<A, B>() {

	override fun getCollisionEvent(a: A, b: B): CollisionEvent3D.Builder {
		val e = origin.getCollisionEvent(b, a)
		e.setNormal(-e.normal)
		return e
	}

	override fun isIntersect(a: A, b: B): Boolean {
		return origin.isIntersect(b, a)
	}

	override fun toString(): String {
		return "SwapBinaryOperation2D [origin=$origin]"
	}
}
