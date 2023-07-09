package com.greentree.commons.geometry.geom2d.atomic

import com.greentree.commons.math.MathLine1D
import com.greentree.commons.math.vector.AbstractVector2f
import com.greentree.commons.math.vector.Vector2f

object Circle : SimpleShape2D {

	override fun projection(normal: AbstractVector2f): MathLine1D {
		return MathLine1D(-1f, 1f)
	}

	override fun isInside(point: AbstractVector2f): Boolean {
		return point.lengthSquared() <= 1
	}

	override fun nearestPoint(point: AbstractVector2f): AbstractVector2f {
		return Vector2f(point).normalize(1)
	}

	override fun normals(): Iterable<AbstractVector2f> {
		return NormalsVector2fCollection()
	}

	override fun points(): Iterable<AbstractVector2f> {
		return NormalsVector2fCollection()
	}
}