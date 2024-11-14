package com.greentree.commons.geometry.geom2d.shape

import com.greentree.commons.math.vector.AbstractVector2f

data class Polygon2D(
	override val points: Array<AbstractVector2f>,
) : FiniteShape2D {

	constructor(points: Iterable<AbstractVector2f>) : this(points.asCollection().toTypedArray())

	override fun equals(other: Any?): Boolean {
		if(this === other) return true
		if(javaClass != other?.javaClass) return false

		other as Polygon2D

		return points.contentEquals(other.points)
	}

	override fun hashCode(): Int {
		return points.contentHashCode()
	}
}

private fun <T> Iterable<T>.asCollection() = if(this is Collection<T>)
	this
else
	toCollection(mutableListOf())
