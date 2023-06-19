package com.greentree.commons.math.vector.list

import com.greentree.commons.math.vector.AbstractVector3f
import com.greentree.commons.util.iterator.EmptyIterator
import org.joml.Matrix3fc
import org.joml.Matrix4fc

object EmptyVector3fCollection : AbstractVector3fCollection {

	override val size: Int
		get() = 0

	override fun contains(element: AbstractVector3f): Boolean {
		return false
	}

	override fun containsAll(elements: Collection<AbstractVector3f>): Boolean {
		return false
	}

	override fun isEmpty(): Boolean {
		return true
	}

	override fun times(other: Float): AbstractVector3fCollection {
		return this
	}

	override fun times(other: Matrix3fc): AbstractVector3fCollection {
		return this
	}

	override fun times(other: Matrix4fc): AbstractVector3fCollection {
		return this
	}

	override fun iterator(): Iterator<AbstractVector3f> {
		return EmptyIterator.instance()
	}
}