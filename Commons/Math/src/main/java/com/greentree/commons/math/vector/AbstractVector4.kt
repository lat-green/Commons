package com.greentree.commons.math.vector

interface AbstractVector4<T> : AbstractVector<T> {

	val x: T
	val y: T
	val z: T
	val w: T

	fun x() = x
	fun y() = y
	fun z() = z
	fun w() = w

	override val size: Int
		get() = 4

	override fun component1(): T {
		return x
	}

	override fun component2(): T {
		return y
	}

	override fun component3(): T {
		return z
	}

	override fun component4(): T {
		return w
	}

	override fun get(index: Int): T {
		return when(index) {
			0 -> x
			1 -> y
			2 -> z
			3 -> w
			else -> throw IndexOutOfBoundsException()
		}
	}

	override fun iterator(): Iterator<T> {
		return listOf(x, y, z, w).iterator()
	}
}
