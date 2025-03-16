package com.greentree.commons.geometry.geom2d.shape

import org.joml.Vector2fc

data class Polygon2D(
	override val points: Array<Vector2fc>,
) : FiniteShape2D {

	constructor(points: Iterable<Vector2fc>) : this(points.asCollection().toTypedArray())

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
