package com.greentree.commons.math.vector

interface AbstractMutableVector3<T> : AbstractVector3<T>, AbstractMutableVector<T> {

	override var x: T
	override var y: T
	override var z: T

	fun x(value: T): AbstractMutableVector3<T> {
		this.x = value
		return this
	}

	fun y(value: T): AbstractMutableVector3<T> {
		this.y = value
		return this
	}

	fun z(value: T): AbstractMutableVector3<T> {
		this.z = value
		return this
	}

	fun set(x: T, y: T, z: T): AbstractMutableVector3<T> {
		this.x = x
		this.y = y
		this.z = z
		return this
	}

	fun set(xyz: AbstractVector3<T>): AbstractMutableVector3<T> {
		this.x = xyz.x
		this.y = xyz.y
		this.z = xyz.z
		return this
	}

	fun set(xy: AbstractVector2<T>, z: T): AbstractMutableVector3<T> {
		this.x = xy.x
		this.y = xy.y
		this.z = z
		return this
	}

	fun set(x: T, yz: AbstractVector2<T>): AbstractMutableVector3<T> {
		this.x = x
		this.y = yz.x
		this.z = yz.y
		return this
	}

	override fun set(index: Int, value: T) {
		return when(index) {
			0 -> x = value
			1 -> y = value
			2 -> z = value
			else -> throw IndexOutOfBoundsException()
		}
	}

	override fun assign(other: AbstractVector<out T>) {
		if(other.size == 3) {
			x = other[0]
			y = other[1]
			z = other[2]
		} else
			throw IndexOutOfBoundsException()
	}
}
