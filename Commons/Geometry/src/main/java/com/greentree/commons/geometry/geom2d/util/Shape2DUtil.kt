package com.greentree.commons.geometry.geom2d.util

import com.greentree.commons.geometry.geom2d.shape.Circle2D
import com.greentree.commons.geometry.geom2d.shape.Rectangle2D
import com.greentree.commons.math.Mathf.sqrt
import org.joml.Vector2f
import org.joml.Vector2fc
import org.joml.divAssign
import org.joml.minus
import org.joml.plusAssign

fun boundingBox(points: Array<Vector2fc>): Rectangle2D {
	val x = points.map { it.x() }
	val y = points.map { it.y() }
	return Rectangle2D(x.min(), y.min(), x.max(), y.max())
}

fun boundingCircle(points: Array<Vector2fc>): Circle2D {
	val center = Vector2f()
	points.fold(center) { acc, b ->
		acc += b
		acc
	}
	center /= points.size.toFloat()
	val radius = points.maxOf { (it - center).lengthSquared() }
	return Circle2D(center, sqrt(radius))
}