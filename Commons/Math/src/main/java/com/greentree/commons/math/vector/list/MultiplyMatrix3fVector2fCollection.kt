package com.greentree.commons.math.vector.list

import com.greentree.commons.math.vector.AbstractVector2f
import com.greentree.commons.math.vector.div
import org.joml.Matrix3fc

class MultiplyMatrix3fVector2fCollection(
	private val collection: AbstractVector2fCollection,
	private val model: Matrix3fc,
) :
	AbstractVector2fCollection {

	override val size: Int
		get() = collection.size

	override fun contains(element: AbstractVector2f): Boolean {
		return (element / model) in collection
	}

	override fun isEmpty(): Boolean {
		return collection.isEmpty()
	}

	override fun iterator(): Iterator<AbstractVector2f> {
		return collection.map { it * model }.iterator()
	}
}
