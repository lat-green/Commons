package com.greentree.commons.math

import org.joml.Vector2f
import org.joml.Vector2fc
import org.joml.Vector3f
import org.joml.Vector3fc

val Vector3fc.x
	get() = x()
val Vector3fc.y
	get() = y()
val Vector3fc.z
	get() = z()

fun vec3f(xy: Vector2fc, z: Float) = vec3f(xy.x, xy.y, z)
fun vec3f(x: Float, y: Float, z: Float) = Vector3f(x, y, z)

fun Vector3fc.cross(other: Vector3fc) =
	Vector3f(y * other.z - z * other.y, z * other.x - x * other.z, x * other.y - y * other.x)

fun Vector3fc.projection(normal: Vector3fc): Vector2f {
	val a = normal.x()
	val b = normal.y()
	val c = normal.z()
	var xAxis: Vector3fc
	var yAxis: Vector3fc = vec3f(a, b, c).normalize()
	xAxis = vec3f(b, -a, 0f)
	if(xAxis.lengthSquared() < Mathf.EPS)
		xAxis = vec3f(0f, c, -b)
	yAxis = yAxis.cross(xAxis)
	val x = dot(xAxis)
	val y = dot(yAxis)
	return vec2f(x, y)
}