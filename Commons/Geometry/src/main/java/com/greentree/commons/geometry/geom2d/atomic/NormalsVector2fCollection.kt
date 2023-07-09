package com.greentree.commons.geometry.geom2d.atomic

import com.greentree.commons.math.vector.AbstractVector2f

class NormalsVector2fCollection : Collection<AbstractVector2f> {

	override val size: Int
		get() = Int.MAX_VALUE

	override fun contains(element: AbstractVector2f) = element.isNormal()

	override fun containsAll(elements: Collection<AbstractVector2f>): Boolean {
		for(e in elements)
			if(contains(e))
				return true
		return false
	}

	override fun isEmpty() = false

	override fun iterator(): Iterator<AbstractVector2f> {
		throw UnsupportedOperationException()
	}
}
