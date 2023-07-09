package com.greentree.commons.geometry.geom2d.atomic

import com.greentree.commons.math.MathLine1D
import com.greentree.commons.math.vector.AbstractVector2f
import com.greentree.commons.math.vector.Vector3f
import org.joml.Matrix2f
import org.joml.Matrix3f
import org.joml.Matrix3fc

interface SimpleShape2D {

	fun projection(normal: AbstractVector2f): MathLine1D

	fun isInside(point: AbstractVector2f): Boolean

	fun nearestPoint(point: AbstractVector2f): AbstractVector2f

	fun normals(): Iterable<AbstractVector2f>

	fun points(): Iterable<AbstractVector2f>
}

fun SimpleShape2D.projection(model: Matrix3fc, normal: AbstractVector2f): MathLine1D {
	var v = normal * Matrix2f(model)
	val len = v.length()
	v = v.normalize(1)
	val ml = projection(v)
	return MathLine1D(ml.min * len, ml.max * len)
}

fun SimpleShape2D.isInside(model: Matrix3fc, point: AbstractVector2f): Boolean {
	val invModelMatrix = Matrix3f()
	model.invert(invModelMatrix)
	val v = Vector3f(point, 1f).times(invModelMatrix)
	return isInside(v.xy())
}

fun SimpleShape2D.nearestPoint(model: Matrix3fc, point: AbstractVector2f): AbstractVector2f {
	val invModelMatrix = Matrix3f()
	model.invert(invModelMatrix)
	val v = Vector3f(point, 1f).times(invModelMatrix)
	return Vector3f(nearestPoint(v.xy()), 1f).times(model).xy()
}

fun SimpleShape2D.normals(model: Matrix3fc): Iterable<out AbstractVector2f> {
	return normals().map { it * Matrix2f(model) }
}

fun SimpleShape2D.points(model: Matrix3fc): Iterable<AbstractVector2f?> {
	return points().map { it * model }
}