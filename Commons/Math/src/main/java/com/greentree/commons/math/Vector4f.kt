package com.greentree.commons.math

import org.joml.Vector2fc
import org.joml.Vector3fc
import org.joml.Vector4f
import org.joml.Vector4fc

val Vector4fc.x
	get() = x()
val Vector4fc.y
	get() = y()
val Vector4fc.z
	get() = z()
val Vector4fc.w
	get() = w()

fun vec4f(xy: Vector2fc, z: Float, w: Float) = Vector4f(xy.x, xy.y, z, w)
fun vec4f(xyz: Vector3fc, w: Float) = Vector4f(xyz.x, xyz.y, xyz.z, w)
fun vec4f(x: Float, y: Float, z: Float, w: Float) = Vector4f(x, y, z, w)