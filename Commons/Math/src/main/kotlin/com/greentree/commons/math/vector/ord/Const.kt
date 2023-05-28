package com.greentree.commons.math.vector.ord

class Const(private val value: Float) : FloatVectorOrd {
    override fun get(): Float {
        return value
    }
}