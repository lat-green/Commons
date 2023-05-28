package com.greentree.commons.math.vector

import org.joml.Matrix2f

interface AbstractVector2f : AbstractFloatVector, AbstractVector2<Float> {

    fun cross(vec: AbstractVector2f): Float {
        return x * vec.y - y * vec.x
    }

    override fun normalize(length: Number): AbstractVector2f {
        return this * (length.toFloat() / length())
    }

    override fun plus(other: Float): AbstractVector2f {
        return super.plus(other) as AbstractVector2f
    }

    override fun div(other: Float): AbstractVector2f {
        return super.div(other) as AbstractVector2f
    }

    override fun times(other: Float): AbstractVector2f {
        return super.times(other) as AbstractVector2f
    }

    override fun minus(other: Float): AbstractVector2f {
        return super.minus(other) as AbstractVector2f
    }

    override fun plus(other: Number): AbstractVector2f {
        return super.plus(other) as AbstractVector2f
    }

    override fun div(other: Number): AbstractVector2f {
        return super.div(other) as AbstractVector2f
    }

    override fun times(other: Number): AbstractVector2f {
        return super.times(other) as AbstractVector2f
    }

    override fun minus(other: Number): AbstractVector2f {
        return super.minus(other) as AbstractVector2f
    }

    override fun plus(other: AbstractVector<out Float>): AbstractVector2f {
        return plus(vec2f(other))
    }

    override fun times(other: AbstractVector<out Float>): AbstractVector2f {
        return times(vec2f(other))
    }

    override fun div(other: AbstractVector<out Float>): AbstractVector2f {
        return div(vec2f(other))
    }

    override fun minus(other: AbstractVector<out Float>): AbstractVector2f {
        return minus(vec2f(other))
    }

    fun plus(other: AbstractVector2f): AbstractVector2f {
        return vec2f(x + other.x, y + other.y)
    }

    fun minus(other: AbstractVector2f): AbstractVector2f {
        return vec2f(x - other.x, y - other.y)
    }

    fun times(other: AbstractVector2f): AbstractVector2f {
        return vec2f(x * other.x, y * other.y)
    }

    fun div(other: AbstractVector2f): AbstractVector2f {
        return vec2f(x / other.x, y / other.y)
    }

    override fun unaryPlus(): AbstractVector2f {
        return vec2f(this)
    }

    override fun unaryMinus(): AbstractVector2f {
        return vec2f(-x, -y)
    }

    operator fun times(mat: Matrix2f): AbstractVector2f {
        return mat.transform(toJoml())!!.toMath()
    }

    fun toJoml(): org.joml.Vector2f {
        return org.joml.Vector2f(x, y)
    }

    override fun toMutable(): AbstractMutableVector2f {
        return Vector2f(x, y)
    }

    companion object {
        val X: AbstractVector2f = FinalVector2f(1f, 0f)
        val Y: AbstractVector2f = FinalVector2f(0f, 1f)

    }
}

fun vec2f(other: AbstractVector<out Float>): AbstractVector2f {
    require(other.size == 2) { "the size of the vectors are different $other.size != 2" }
    return vec2f(other[0], other[1])
}

fun vec2f(x: Float, y: Float): AbstractVector2f {
    return FinalVector2f(x, y)
}

fun org.joml.Vector2f.toMath(): AbstractVector2f {
    return vec2f(x, y)
}