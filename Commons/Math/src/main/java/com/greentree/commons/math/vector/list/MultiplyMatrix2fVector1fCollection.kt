package com.greentree.commons.math.vector.list

import com.greentree.commons.math.vector.vec2f
import org.joml.Matrix2f
import org.joml.Matrix2fc

class MultiplyMatrix2fVector1fCollection(
	private val collection: AbstractVector1fCollection,
	private val other: Matrix2fc,
) :
	AbstractVector1fCollection {

	override val size: Int
		get() = collection.size

	override fun isEmpty() = collection.isEmpty()

	override fun contains(element: Float) = (element / other) in collection

	override fun iterator(): Iterator<Float> = (collection as Iterable<Float>).map { it * other }.iterator()
}

private operator fun Float.times(other: Matrix2fc): Float {
	return (vec2f(this, 1f) * other).x
}

private operator fun Float.div(other: Matrix2fc): Float {
	return times(other.invert(Matrix2f()))
}