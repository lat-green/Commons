package com.greentree.commons.math.vector

class FinalVectorNf(private val values: FloatArray) : AbstractVectorNf {

    override val size
        get() = values.size

    constructor(length: Int) : this(FloatArray(length))

    override fun get(i: Int): Float {
        return values[i]
    }

}