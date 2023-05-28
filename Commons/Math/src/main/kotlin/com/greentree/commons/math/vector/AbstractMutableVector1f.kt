package com.greentree.commons.math.vector

interface AbstractMutableVector1f : AbstractVector1f, AbstractMutableVector1<Float>, AbstractMutableFloatVector {

    override fun toMutable(): AbstractMutableVector1f {
        return this
    }
}
