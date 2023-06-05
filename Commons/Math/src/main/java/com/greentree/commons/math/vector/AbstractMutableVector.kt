package com.greentree.commons.math.vector

interface AbstractMutableVector<T> : AbstractVector<T> {

	operator fun set(index: Int, value: T)

	operator fun divAssign(other: T) {
		return assign(div(other))
	}

	operator fun plusAssign(other: T) {
		return assign(plus(other))
	}

	operator fun minusAssign(other: T) {
		return assign(minus(other))
	}

	operator fun timesAssign(other: T) {
		return assign(times(other))
	}

	operator fun divAssign(other: AbstractVector<out T>) {
		return assign(div(other))
	}

	operator fun plusAssign(other: AbstractVector<out T>) {
		return assign(plus(other))
	}

	operator fun minusAssign(other: AbstractVector<out T>) {
		return assign(minus(other))
	}

	operator fun timesAssign(other: AbstractVector<out T>) {
		return assign(times(other))
	}

	fun assign(other: AbstractVector<out T>) {
		checkSize(other)
		for(i in 0 until size) {
			set(i, other[i])
		}
	}

	override fun toMutable(): AbstractMutableVector<T> {
		return this
	}
}