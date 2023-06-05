package com.greentree.commons.math.vector

interface AbstractMutableVector4<T> : AbstractVector4<T>, AbstractMutableVector<T> {

	override var x: T
	override var y: T
	override var z: T
	override var w: T

	fun x(value: T): AbstractMutableVector4<T> {
		this.x = value
		return this
	}

	fun y(value: T): AbstractMutableVector4<T> {
		this.y = value
		return this
	}

	fun z(value: T): AbstractMutableVector4<T> {
		this.z = value
		return this
	}

	fun w(value: T): AbstractMutableVector4<T> {
		this.w = value
		return this
	}

	fun set(x: T, y: T, z: T, w: T): AbstractMutableVector4<T> {
		this.x = x
		this.y = y
		this.z = z
		this.w = w
		return this
	}

	fun set(xyzw: AbstractVector4<T>): AbstractMutableVector4<T> {
		this.x = xyzw.x
		this.y = xyzw.y
		this.z = xyzw.z
		this.w = xyzw.w
		return this
	}

	fun set(x: T, yzw: AbstractVector3<T>): AbstractMutableVector4<T> {
		this.x = x
		this.y = yzw.x
		this.z = yzw.y
		this.w = yzw.z
		return this
	}

	fun set(xyz: AbstractVector3<T>, w: T): AbstractMutableVector4<T> {
		this.x = xyz.x
		this.y = xyz.y
		this.z = xyz.z
		this.w = w
		return this
	}

	fun set(xy: AbstractVector2<T>, z: T, w: T): AbstractMutableVector4<T> {
		this.x = xy.x
		this.y = xy.y
		this.z = z
		this.w = w
		return this
	}

	fun set(x: T, yz: AbstractVector2<T>, w: T): AbstractMutableVector4<T> {
		this.x = x
		this.y = yz.x
		this.z = yz.y
		this.w = w
		return this
	}

	fun set(x: T, y: T, zw: AbstractVector2<T>): AbstractMutableVector4<T> {
		this.x = x
		this.y = y
		this.z = zw.x
		this.w = zw.y
		return this
	}

	override fun set(index: Int, value: T) {
		return when(index) {
			0 -> x = value
			1 -> y = value
			2 -> z = value
			3 -> w = value
			else -> throw IndexOutOfBoundsException()
		}
	}

	override fun assign(other: AbstractVector<out T>) {
		if(other.size == 4) {
			x = other[0]
			y = other[1]
			z = other[2]
			w = other[3]
		} else
			throw IndexOutOfBoundsException()
	}
}
