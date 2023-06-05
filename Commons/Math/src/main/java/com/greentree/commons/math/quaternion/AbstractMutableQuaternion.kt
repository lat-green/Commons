package com.greentree.commons.math.quaternion

import com.greentree.commons.math.vector.AbstractMutableVector4f
import com.greentree.commons.math.vector.AbstractVector3f
import com.greentree.commons.math.vector.AbstractVector4
import org.joml.Math

interface AbstractMutableQuaternion : AbstractQuaternion, AbstractMutableVector4f {

	fun rotateX(angle: Float): AbstractMutableQuaternion {
		val sin = Math.sin(angle * 0.5f)
		val cos = Math.cosFromSin(sin, angle * 0.5f)
		return set(
			w * sin + x * cos,
			y * cos + z * sin,
			z * cos - y * sin,
			w * cos - x * sin
		)
	}

	fun rotateY(angle: Float): AbstractMutableQuaternion {
		val sin = Math.sin(angle * 0.5f)
		val cos = Math.cosFromSin(sin, angle * 0.5f)
		return set(
			x * cos - z * sin,
			w * sin + y * cos,
			x * sin + z * cos,
			w * cos - y * sin
		)
	}

	fun rotateZ(angle: Float): AbstractMutableQuaternion {
		val sin = Math.sin(angle * 0.5f)
		val cos = Math.cosFromSin(sin, angle * 0.5f)
		return set(
			x * cos + y * sin,
			y * cos - x * sin,
			w * sin + z * cos,
			w * cos - z * sin
		)
	}

	fun lookAlong(dir: AbstractVector3f, up: AbstractVector3f): AbstractMutableQuaternion {
		return lookAlong(dir.x(), dir.y(), dir.z(), up.x(), up.y(), up.z())
	}

	fun lookAlong(
		dirX: Float,
		dirY: Float,
		dirZ: Float,
		upX: Float,
		upY: Float,
		upZ: Float,
	): AbstractMutableQuaternion {
		// Normalize direction
		val invDirLength = Math.invsqrt(dirX * dirX + dirY * dirY + dirZ * dirZ)
		val dirnX = -dirX * invDirLength
		val dirnY = -dirY * invDirLength
		val dirnZ = -dirZ * invDirLength
		// left = up x dir
		var leftX: Float
		var leftY: Float
		var leftZ: Float
		leftX = upY * dirnZ - upZ * dirnY
		leftY = upZ * dirnX - upX * dirnZ
		leftZ = upX * dirnY - upY * dirnX
		// normalize left
		val invLeftLength = Math.invsqrt(leftX * leftX + leftY * leftY + leftZ * leftZ)
		leftX *= invLeftLength
		leftY *= invLeftLength
		leftZ *= invLeftLength
		// up = direction x left
		val upnX = dirnY * leftZ - dirnZ * leftY
		val upnY = dirnZ * leftX - dirnX * leftZ
		val upnZ = dirnX * leftY - dirnY * leftX
		/* Convert orthonormal basis vectors to quaternion */
		val x: Float
		val y: Float
		val z: Float
		val w: Float
		var t: Double
		val tr = (leftX + upnY + dirnZ).toDouble()
		if(tr >= 0.0) {
			t = Math.sqrt(tr + 1.0)
			w = (t * 0.5).toFloat()
			t = 0.5 / t
			x = ((dirnY - upnZ) * t).toFloat()
			y = ((leftZ - dirnX) * t).toFloat()
			z = ((upnX - leftY) * t).toFloat()
		} else {
			if(leftX > upnY && leftX > dirnZ) {
				t = Math.sqrt(1.0 + leftX - upnY - dirnZ)
				x = (t * 0.5).toFloat()
				t = 0.5 / t
				y = ((leftY + upnX) * t).toFloat()
				z = ((dirnX + leftZ) * t).toFloat()
				w = ((dirnY - upnZ) * t).toFloat()
			} else if(upnY > dirnZ) {
				t = Math.sqrt(1.0 + upnY - leftX - dirnZ)
				y = (t * 0.5).toFloat()
				t = 0.5 / t
				x = ((leftY + upnX) * t).toFloat()
				z = ((upnZ + dirnY) * t).toFloat()
				w = ((leftZ - dirnX) * t).toFloat()
			} else {
				t = Math.sqrt(1.0 + dirnZ - leftX - upnY)
				z = (t * 0.5).toFloat()
				t = 0.5 / t
				x = ((dirnX + leftZ) * t).toFloat()
				y = ((upnZ + dirnY) * t).toFloat()
				w = ((upnX - leftY) * t).toFloat()
			}
		}
		return set(
			Math.fma(this.w, x, Math.fma(this.x, w, Math.fma(this.y, z, -this.z * y))),
			Math.fma(this.w, y, Math.fma(-this.x, z, Math.fma(this.y, w, this.z * x))),
			Math.fma(this.w, z, Math.fma(this.x, y, Math.fma(-this.y, x, this.z * w))),
			Math.fma(this.w, w, Math.fma(-this.x, x, Math.fma(-this.y, y, -this.z * z)))
		)
	}

	fun identity(): AbstractMutableQuaternion {
		x = 0f
		y = 0f
		z = 0f
		w = 1f
		return this
	}

	override fun set(xyzw: AbstractVector4<Float>): AbstractMutableQuaternion {
		super.set(xyzw)
		return this
	}

	override fun set(x: Float, y: Float, z: Float, w: Float): AbstractMutableQuaternion {
		super.set(x, y, z, w)
		return this
	}

	fun rotationX(angle: Float): AbstractMutableQuaternion {
		val sin = Math.sin(angle * 0.5f)
		val cos = Math.cosFromSin(sin, angle * 0.5f)
		return set(sin, 0f, 0f, cos)
	}

	fun rotationY(angle: Float): AbstractMutableQuaternion {
		val sin = Math.sin(angle * 0.5f)
		val cos = Math.cosFromSin(sin, angle * 0.5f)
		return set(0f, sin, 0f, cos)
	}

	fun rotationZ(angle: Float): AbstractMutableQuaternion {
		val sin = Math.sin(angle * 0.5f)
		val cos = Math.cosFromSin(sin, angle * 0.5f)
		return set(0f, 0f, sin, cos)
	}
}