package com.greentree.commons.math.vector.list

import com.greentree.commons.math.vector.AbstractVector3f

class Vector3fCollection(vectors: Collection<AbstractVector3f>) : AbstractCollection<AbstractVector3f>(),
	AbstractVector3fCollection {

	private val vectors: Collection<AbstractVector3f>

	init {
		this.vectors = ArrayList(vectors)
	}

	override fun containsAll(elements: Collection<AbstractVector3f>) = super<AbstractCollection>.containsAll(elements)
	
	override val size: Int
		get() = vectors.size

	override fun iterator(): Iterator<AbstractVector3f> {
		return vectors.iterator()
	}
}