package com.greentree.commons.math.vector.list

import com.greentree.commons.math.vector.AbstractVector3f
import com.greentree.commons.math.vector.div
import org.joml.Matrix4fc

class MultiplyMatrix4fVector3fCollection(
	private val collection: AbstractVector3fCollection,
	private val other: Matrix4fc,
) :
	AbstractVector3fCollection {

	override val size: Int
		get() = collection.size

	override fun contains(element: AbstractVector3f): Boolean {
		return (element / other) in collection
	}

	override fun isEmpty(): Boolean {
		return collection.isEmpty()
	}

	override fun iterator(): Iterator<AbstractVector3f> {
		return collection.map { it * other }.iterator()
	}
}
