package com.greentree.commons.geometry.geom3d.face

import com.greentree.commons.geometry.geom2d.Shape2DUtil
import com.greentree.commons.geometry.geom3d.IShape3D
import com.greentree.commons.math.MathPlane3D
import com.greentree.commons.math.Mathf.abs
import com.greentree.commons.math.projection
import org.joml.Vector3fc
import org.joml.minus

interface Face : IShape3D {

	override fun minPoint(point: Vector3fc): Vector3fc {
		return faces.map { it.minPoint(point) }.minBy { it.distanceSquared(point) }
	}

	override fun getFaces(): Array<Face> = arrayOf(this)

	override fun getArea(): Float {
		val v1 = p1.minus(p2)
		val v2 = p3.minus(p2)
		return abs(v1.cross(v2).length()) / 2
	}

	override fun getFacesCount(): Int {
		return 1
	}

	val p1: Vector3fc
	val p2: Vector3fc
	val p3: Vector3fc
	val normal: Vector3fc
		get() = mathPlane.normal
	val mathPlane: MathPlane3D
		get() = MathPlane3D(p1, p2, p3)

	fun isClockwise(normal: Vector3fc): Boolean {
		val p1 = p1.projection(normal)
		val p2 = p2.projection(normal)
		val p3 = p3.projection(normal)
		return Shape2DUtil.isClockwise(p1, p2, p3)
	}

	val isConvex: Boolean
		get() = mathPlane.getD() > 0
}
