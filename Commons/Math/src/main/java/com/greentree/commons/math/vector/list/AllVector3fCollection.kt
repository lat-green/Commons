package com.greentree.commons.math.vector.list

import com.greentree.commons.math.vector.AbstractVector3f
import org.joml.Matrix3fc
import org.joml.Matrix4fc

object AllVector3fCollection : AbstractVector3fCollection {

	override val size: Int
		get() = Int.MAX_VALUE

	override fun contains(element: AbstractVector3f): Boolean {
		return true
	}

	override fun containsAll(elements: Collection<AbstractVector3f>): Boolean {
		return true
	}

	override fun isEmpty(): Boolean {
		return false
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
		throw UnsupportedOperationException()
	}
}