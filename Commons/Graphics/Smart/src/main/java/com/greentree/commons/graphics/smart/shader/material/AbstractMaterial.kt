package com.greentree.commons.graphics.smart.shader.material

import com.greentree.commons.graphics.smart.texture.Texture
import com.greentree.commons.image.Color
import com.greentree.commons.math.vector.AbstractVector1f
import com.greentree.commons.math.vector.AbstractVector2f
import com.greentree.commons.math.vector.AbstractVector3f
import com.greentree.commons.math.vector.AbstractVector4f
import org.joml.Matrix4f

interface AbstractMaterial {

	fun put(name: String, vector: AbstractVector1f) = put(name, vector.x)
	fun put(name: String, vector: AbstractVector2f) = put(name, vector.x, vector.y)
	fun put(name: String, vector: AbstractVector3f) = put(name, vector.x, vector.y, vector.z)
	fun put(name: String, vector: AbstractVector4f) = put(name, vector.x, vector.y, vector.z, vector.w)

	fun put(name: String, x1: Float)
	fun put(name: String, x1: Float, x2: Float)
	fun put(name: String, x1: Float, x2: Float, x3: Float)
	fun put(name: String, x1: Float, x2: Float, x3: Float, x4: Float)

	fun put(name: String, x1: Int)
	fun put(name: String, x1: Int, x2: Int)
	fun put(name: String, x1: Int, x2: Int, x3: Int)
	fun put(name: String, x1: Int, x2: Int, x3: Int, x4: Int)

	fun put(name: String, texture: Texture)

	fun put(name: String, matrix: Matrix4f) = matrix.run {
		put(
			name,
			m00(), m01(), m02(), m03(),
			m10(), m11(), m12(), m13(),
			m20(), m21(), m22(), m23(),
			m30(), m31(), m32(), m33()
		)
	}

	fun put(
		name: String,
		m00: Float, m01: Float, m02: Float, m03: Float,
		m10: Float, m11: Float, m12: Float, m13: Float,
		m20: Float, m21: Float, m22: Float, m23: Float,
		m30: Float, m31: Float, m32: Float, m33: Float,
	)

	fun putRGB(name: String, color: Color) = put(name, color.r, color.g, color.b)
	fun putRGBA(name: String, color: Color) = put(name, color.r, color.g, color.b, color.a)
}
