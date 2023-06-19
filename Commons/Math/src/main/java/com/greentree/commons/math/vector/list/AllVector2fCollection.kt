package com.greentree.commons.math.vector.list

import com.greentree.commons.math.vector.AbstractVector2f
import org.joml.Matrix2fc
import org.joml.Matrix3fc

object AllVector2fCollection : AbstractVector2fCollection {

	override val size: Int
		get() = Int.MAX_VALUE

	override fun contains(element: AbstractVector2f): Boolean {
		return true
	}

	override fun containsAll(elements: Collection<AbstractVector2f>): Boolean {
		return true
	}

	override fun isEmpty(): Boolean {
		return false
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
		throw UnsupportedOperationException()
	}
}