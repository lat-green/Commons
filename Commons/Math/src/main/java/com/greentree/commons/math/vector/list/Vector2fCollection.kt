package com.greentree.commons.math.vector.list

import com.greentree.commons.math.vector.AbstractVector2f

class Vector2fCollection(vectors: Collection<AbstractVector2f>) : AbstractCollection<AbstractVector2f>(),
	AbstractVector2fCollection {

	private val vectors: Collection<AbstractVector2f>

	init {
		this.vectors = ArrayList(vectors)
	}

	override fun containsAll(elements: Collection<AbstractVector2f>) = super<AbstractCollection>.containsAll(elements)

	override val size: Int
		get() = vectors.size

	override fun iterator(): Iterator<AbstractVector2f> {
		return vectors.iterator()
	}
}