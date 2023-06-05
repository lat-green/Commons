package com.greentree.commons.math.vector.ord

import com.greentree.commons.math.vector.AbstractMutableFloatVector

/**
 * @author Arseny Latyshev
 */
class OrdsVectorNf(private val ords: List<FloatVectorOrd>) : AbstractMutableFloatVector {

    override val size: Int
        get() = ords.size

    override fun get(index: Int): Float {
        return ords[index].get()
    }

    override fun set(index: Int, x: Float) {
        val ord = ords[index]
        if (ord is ChangeableFloatVectorOrd) ord.set(x) else throw UnsupportedOperationException("change of $this")
    }

}