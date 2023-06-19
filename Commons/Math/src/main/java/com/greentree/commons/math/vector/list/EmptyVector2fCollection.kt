package com.greentree.commons.math.vector.list

import com.greentree.commons.math.vector.AbstractVector2f
import com.greentree.commons.util.iterator.EmptyIterator
import org.joml.Matrix2fc
import org.joml.Matrix3fc

object EmptyVector2fCollection : AbstractVector2fCollection {

	override val size: Int
		get() = 0

	override fun contains(element: AbstractVector2f): Boolean {
		return false
	}

	override fun containsAll(elements: Collection<AbstractVector2f>): Boolean {
		return false
	}

	override fun isEmpty(): Boolean {
		return true
	}

	override fun times(other: Float): AbstractVector2fCollection {
		return this
	}

	override fun times(other: Matrix3fc): AbstractVector2fCollection {
		return this
	}

	override fun times(other: Matrix2fc): AbstractVector2fCollection {
		return this
	}

	override fun iterator(): Iterator<AbstractVector2f> {
		return EmptyIterator.instance()
	}
}