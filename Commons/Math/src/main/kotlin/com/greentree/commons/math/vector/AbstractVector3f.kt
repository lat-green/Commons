package com.greentree.commons.math.vector

import org.joml.Matrix3f

interface AbstractVector3f : AbstractFloatVector, AbstractVector3<Float> {

    fun cross(other: AbstractVector3f): AbstractVector3f {
        val result = Vector3f()
        result.x = y * other.z - z * other.y
        result.y = z * other.x - x * other.z
        result.z = x * other.y - y * other.x
        return result
    }

    operator fun times(mat: Matrix3f): AbstractVector3f {
        return mat.transform(toJoml())!!.toMath()
    }

    fun toJoml(): org.joml.Vector3f {
        return org.joml.Vector3f(x, y, z)
    }

    companion object {
        val X: AbstractVector3f = FinalVector3f(1f, 0f, 0f)
        val Y: AbstractVector3f = FinalVector3f(0f, 1f, 0f)
        val Z: AbstractVector3f = FinalVector3f(0f, 0f, 1f)
    }
}

fun org.joml.Vector3f.toMath(): AbstractVector3f {
    return Vector3f(x, y, z)
}