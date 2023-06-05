package com.greentree.commons.math.vector

interface AbstractMutableVector1<T> : AbstractVector1<T>, AbstractMutableVector<T> {

	override var x: T

	fun set(value: T) {
		x = value
	}

	fun x(value: T) {
		this.x = value
	}

	override fun set(index: Int, value: T) {
		return when(index) {
			0 -> x = value
			else -> throw IndexOutOfBoundsException()
		}
	}

	override fun assign(other: AbstractVector<out T>) {
		if(other.size == 1) {
			x = other[0]
		} else
			throw IndexOutOfBoundsException()
	}
}
