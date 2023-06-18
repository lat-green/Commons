package com.greentree.commons.math.vector.list

import com.greentree.commons.math.vector.AbstractVector3f
import org.joml.Matrix3fc
import org.joml.Matrix4fc

interface AbstractVector3fCollection : Collection<AbstractVector3f> {

	override fun containsAll(elements: Collection<AbstractVector3f>): Boolean {
		return elements.all { contains(it) }
	}

	fun times(other: Float): AbstractVector3fCollection {
		return MultiplyFloatVector3fCollection(this, other)
	}

	fun times(other: Matrix3fc): AbstractVector3fCollection {
		return MultiplyMatrix3fVector3fCollection(this, other)
	}

	fun times(other: Matrix4fc): AbstractVector3fCollection {
		return MultiplyMatrix4fVector3fCollection(this, other)
	}
}

fun AbstractVector3fCollection.times(other: Number): AbstractVector3fCollection {
	return times(other.toFloat())
}