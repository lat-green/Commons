package com.greentree.commons.math.vector

interface AbstractMutableVector2f : AbstractVector2f, AbstractMutableVector2<Float>, AbstractMutableFloatVector {

    override fun toMutable(): AbstractMutableVector2f {
        return this
    }

}
