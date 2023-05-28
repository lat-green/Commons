package com.greentree.commons.math.vector.ord

import com.greentree.commons.math.vector.AbstractFloatVector

/**
 * @author Arseny Latyshev
 */
class OrdsFloatVectorBuilder {
    private val ords: MutableList<FloatVectorOrd> = ArrayList()
    fun AddOrd(ord: FloatVectorOrd): OrdsFloatVectorBuilder {
        ords.add(ord)
        return this
    }

    fun addValue(): OrdsFloatVectorBuilder {
        return AddOrd(Value())
    }

    fun addLink(vec: AbstractFloatVector, index: Int): OrdsFloatVectorBuilder {
        return AddOrd(Link(vec, index))
    }

    fun addValue(value: Float): OrdsFloatVectorBuilder {
        return AddOrd(Value(value))
    }

    fun addConst(value: Float): OrdsFloatVectorBuilder {
        return AddOrd(Const(value))
    }

    fun biuld(): OrdsVectorNf {
        return OrdsVectorNf(ords)
    }
}