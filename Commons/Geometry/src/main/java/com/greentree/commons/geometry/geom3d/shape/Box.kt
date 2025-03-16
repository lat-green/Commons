package com.greentree.commons.geometry.geom3d.shape

import com.greentree.commons.geometry.geom3d.IShape3D
import com.greentree.commons.geometry.geom3d.face.Face
import com.greentree.commons.geometry.geom3d.face.FaceImpl
import com.greentree.commons.math.vec3f
import org.joml.Vector3f
import org.joml.plus
import org.joml.times

class Box(pos: Vector3f, scale: Vector3f) : IShape3D {

	private val FACES: Array<Face>

	init {
		val p000 = (Vector3f(0f, 0f, 0f) + vec3f(-.5f, -.5f, -.5f)).times(scale + pos)
		val p100 = (Vector3f(1f, 0f, 0f) + vec3f(-.5f, -.5f, -.5f)).times(scale + pos)
		val p010 = (Vector3f(0f, 1f, 0f) + vec3f(-.5f, -.5f, -.5f)).times(scale + pos)
		val p110 = (Vector3f(1f, 1f, 0f) + vec3f(-.5f, -.5f, -.5f)).times(scale + pos)
		val p001 = (Vector3f(0f, 0f, 1f) + vec3f(-.5f, -.5f, -.5f)).times(scale + pos)
		val p101 = (Vector3f(1f, 0f, 1f) + vec3f(-.5f, -.5f, -.5f)).times(scale + pos)
		val p011 = (Vector3f(0f, 1f, 1f) + vec3f(-.5f, -.5f, -.5f)).times(scale + pos)
		val p111 = (Vector3f(1f, 1f, 1f) + vec3f(-.5f, -.5f, -.5f)).times(scale + pos)
		FACES = arrayOf(
			FaceImpl(p010, p100, p000),
			FaceImpl(p110, p100, p010),
			FaceImpl(p101, p011, p001),
			FaceImpl(p101, p111, p011),
			FaceImpl(p000, p001, p010),
			FaceImpl(p010, p001, p011),
			FaceImpl(p100, p110, p101),
			FaceImpl(p110, p111, p101),
			FaceImpl(p000, p001, p100),
			FaceImpl(p100, p001, p101),
			FaceImpl(p010, p110, p011),
			FaceImpl(p110, p111, p011),
		)
	}

	override fun getFaces(): Array<Face> {
		return FACES
	}
}
