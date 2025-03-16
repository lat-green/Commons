package com.greentree.commons.math

import org.joml.Vector3f
import org.joml.Vector3fc
import org.joml.plus
import org.joml.unaryMinus
import java.util.*

class MathLine3D(
	point: Vector3fc,
	vector: Vector3fc,
) {

	private val point: Vector3fc = Vector3f(point)
	private var vector: Vector3fc = Vector3f(if(vector.x < 0) -vector else vector).normalize()

	fun p1(): Vector3fc {
		return vector
	}

	fun p2(): Vector3fc {
		return vector.plus(point)
	}

	override fun hashCode(): Int {
		return Objects.hash(point, vector)
	}

	override fun equals(obj: Any?): Boolean {
		if(this === obj) return true
		if(obj == null || javaClass != obj.javaClass) return false
		val other = obj as MathLine3D
		return point == other.point && vector == other.vector
	}

	override fun toString(): String {
		return "MathLine3D [vector=$vector, point=$point]"
	}
}
