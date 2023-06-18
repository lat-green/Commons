package com.greentree.commons.math.vector.list

import com.greentree.commons.math.vector.AbstractVector2f

class Function2fVector1fCollection(
	private val collection: AbstractVector2fCollection,
	private val function: MathFunction<AbstractVector2f, Float>,
) : AbstractVector1fCollection {

	override val size: Int
		get() = collection.size

	override fun contains(element: Float): Boolean {
		return collection.contains(function.invokeInverse(element))
	}

	override fun isEmpty() = collection.isEmpty()

	override fun iterator(): Iterator<Float> {
		return (collection as Iterable<AbstractVector2f>).map(function).iterator()
	}
}
