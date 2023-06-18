package com.greentree.commons.math.vector.list

import com.greentree.commons.math.vector.AbstractVector2f

class Function2fVector2fCollection(
	private val collection: AbstractVector2fCollection,
	private val function: MathFunction<AbstractVector2f, AbstractVector2f>,
) : AbstractVector2fCollection {

	override val size: Int
		get() = collection.size

	override fun contains(element: AbstractVector2f): Boolean {
		return collection.contains(function.invokeInverse(element))
	}

	override fun isEmpty() = collection.isEmpty()

	override fun iterator(): Iterator<AbstractVector2f> {
		return (collection as Iterable<AbstractVector2f>).map(function).iterator()
	}
}
