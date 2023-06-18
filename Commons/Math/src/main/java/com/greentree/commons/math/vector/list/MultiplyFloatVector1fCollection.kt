package com.greentree.commons.math.vector.list

class MultiplyFloatVector1fCollection(private val collection: AbstractVector1fCollection, private val other: Float) :
	AbstractVector1fCollection {

	override fun max(): Float {
		if(other > 0)
			return collection.max() * other
		return collection.min() * other
	}

	override fun min(): Float {
		if(other > 0)
			return collection.min() * other
		return collection.max() * other
	}

	override val size: Int
		get() = collection.size

	override fun contains(element: Float) = (element / other) in collection

	override fun iterator(): Iterator<Float> = (collection as Iterable<Float>).map { it * other }.iterator()
}
