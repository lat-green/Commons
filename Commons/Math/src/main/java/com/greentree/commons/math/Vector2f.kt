package com.greentree.commons.math

import com.greentree.commons.math.Mathf.abs
import org.joml.Vector2f
import org.joml.Vector2fc

val Vector2fc.x
	get() = x()
val Vector2fc.y
	get() = y()

fun vec2f(v: Vector2fc) = vec2f(v.x, v.y)
fun vec2f(x: Float, y: Float) = Vector2f(x, y)

fun Vector2fc.projection(normal: Vector2fc): Float {
	val m = MathLine2D(normal).minPoint(this)
	val c = cross(normal)
	return c * m.length() / abs(c)
}

fun Vector2fc.normalize(length: Float = 1f): Vector2f = normalize(length, Vector2f())

fun <T : Vector2fc> T.magnitude(length: Float) = if(lengthSquared() < Mathf.EPS)
	this
else
	normalize(length)

fun Vector2fc.cross(other: Vector2fc) = x * other.y - y * other.x

fun Vector2f.setX(x: Float) = set(x, y)
fun Vector2f.setY(y: Float) = set(x, y)