package com.greentree.commons.math.vector

class VectorNf(private val values: FloatArray) : AbstractMutableFloatVector {

    override val size
        get() = values.size

    constructor(length: Int) : this(FloatArray(length))

    override fun get(i: Int): Float {
        return values[i]
    }

    override fun set(i: Int, x: Float) {
        values[i] = x
    }

}