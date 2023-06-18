package com.greentree.commons.math.vector.list

import com.greentree.commons.math.vector.AbstractVector2f

class MultiplyFloatVector2fCollection(private val collection: AbstractVector2fCollection, private val other: Float) :
	AbstractVector2fCollection {

	override val size: Int
		get() = collection.size

	override fun contains(element: AbstractVector2f): Boolean {
		return (element / other) in collection
	}

	override fun isEmpty(): Boolean {
		return collection.isEmpty()
	}

	override fun iterator(): Iterator<AbstractVector2f> {
		return collection.map { it * other }.iterator()
	}
}
