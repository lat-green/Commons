package com.greentree.commons.math.vector

interface AbstractMutableVector3f : AbstractVector3f, AbstractMutableVector3<Float>, AbstractMutableFloatVector {

    override fun toMutable(): AbstractMutableVector3f {
        return this
    }

}
