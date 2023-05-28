package com.greentree.commons.math.vector

interface AbstractVector1f : AbstractFloatVector {

    val x : Float

    override val size: Int
        get() = 1

    override fun get(index: Int): Float {
        return when(index) {
            0 -> x
            else -> throw IndexOutOfBoundsException()
        }
    }

    override fun length(): Float {
        return x
    }

    override fun lengthSquared(): Float {
        return x * x
    }

    companion object {
        val X: AbstractVector1f = FinalVector1f(1f)
    }
}