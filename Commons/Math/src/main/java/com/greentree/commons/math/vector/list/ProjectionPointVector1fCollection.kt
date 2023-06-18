package com.greentree.commons.math.vector.list

import com.greentree.commons.math.vector.AbstractVector2f

class ProjectionPointVector1fCollection(
	private val collection: AbstractVector2fCollection,
	private val normal: AbstractVector2f,
) :
	AbstractVector1fCollection {

	override val size: Int
		get() = collection.size

	override fun contains(element: Float) = min() <= element && element <= max()

	override fun iterator() =
		(collection as Iterable<AbstractVector2f>).map { it.getProjectionPoint(normal) }.iterator()
}
