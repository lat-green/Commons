package com.greentree.commons.math.quaternion

import com.greentree.commons.math.vector.AbstractVector3f
import com.greentree.commons.math.vector.AbstractVector4f
import com.greentree.commons.math.vector.vec3f
import org.joml.Quaternionf

interface AbstractQuaternion : AbstractVector4f {

	fun rotateYXZ(angleY: Float, angleX: Float, angleZ: Float): AbstractQuaternion {
		val sx = org.joml.Math.sin(angleX * 0.5f)
		val cx = org.joml.Math.cosFromSin(sx, angleX * 0.5f)
		val sy = org.joml.Math.sin(angleY * 0.5f)
		val cy = org.joml.Math.cosFromSin(sy, angleY * 0.5f)
		val sz = org.joml.Math.sin(angleZ * 0.5f)
		val cz = org.joml.Math.cosFromSin(sz, angleZ * 0.5f)
		val yx = cy * sx
		val yy = sy * cx
		val yz = sy * sx
		val yw = cy * cx
		val x = yx * cz + yy * sz
		val y = yy * cz - yx * sz
		val z = yw * sz - yz * cz
		val w = yw * cz + yz * sz
		return Quaternion(
			org.joml.Math.fma(this.w, x, org.joml.Math.fma(this.x, w, org.joml.Math.fma(this.y, z, -this.z * y))),
			org.joml.Math.fma(this.w, y, org.joml.Math.fma(-this.x, z, org.joml.Math.fma(this.y, w, this.z * x))),
			org.joml.Math.fma(this.w, z, org.joml.Math.fma(this.x, y, org.joml.Math.fma(-this.y, x, this.z * w))),
			org.joml.Math.fma(this.w, w, org.joml.Math.fma(-this.x, x, org.joml.Math.fma(-this.y, y, -this.z * z)))
		)
	}

	fun invert(): AbstractQuaternion {
		val invNorm = 1.0f / org.joml.Math.fma(x, x, org.joml.Math.fma(y, y, org.joml.Math.fma(z, z, w * w)))
		return Quaternion(-x * invNorm, -y * invNorm, -z * invNorm, w * invNorm)
	}

	fun times(q: AbstractQuaternion): AbstractQuaternion {
		return Quaternion(
			org.joml.Math.fma(w, q.x(), org.joml.Math.fma(x, q.w(), org.joml.Math.fma(y, q.z(), -z * q.y()))),
			org.joml.Math.fma(w, q.y(), org.joml.Math.fma(-x, q.z(), org.joml.Math.fma(y, q.w(), z * q.x()))),
			org.joml.Math.fma(w, q.z(), org.joml.Math.fma(x, q.y(), org.joml.Math.fma(-y, q.x(), z * q.w()))),
			org.joml.Math.fma(w, q.w(), org.joml.Math.fma(-x, q.x(), org.joml.Math.fma(-y, q.y(), -z * q.z())))
		)
	}

	operator fun times(other: AbstractVector3f): AbstractVector3f {
		val xx = x * x
		val yy = y * y
		val zz = z * z
		val ww = w * w
		val xy = x * y
		val xz = x * z
		val yz = y * z
		val xw = x * w
		val zw = z * w
		val yw = y * w
		val k = 1 / (xx + yy + zz + ww)
		return vec3f(
			Math.fma(
				(xx - yy - zz + ww) * k,
				other.x,
				Math.fma(2 * (xy - zw) * k, other.y, 2 * (xz + yw) * k * other.z)
			),
			Math.fma(
				2 * (xy + zw) * k,
				other.x,
				Math.fma((yy - xx - zz + ww) * k, other.y, 2 * (yz - xw) * k * other.z)
			),
			Math.fma(
				2 * (xz - yw) * k,
				other.x,
				Math.fma(2 * (yz + xw) * k, other.y, (zz - xx - yy + ww) * k * other.z)
			)
		)
	}
}

operator fun AbstractVector3f.times(other: AbstractQuaternion) = other * this

fun AbstractQuaternion.jomlQuaternion() = Quaternionf(x, y, z, w)