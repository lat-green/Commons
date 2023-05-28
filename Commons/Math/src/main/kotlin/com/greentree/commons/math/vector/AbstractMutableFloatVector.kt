package com.greentree.commons.math.vector

interface AbstractMutableFloatVector : AbstractMutableVector<Float>, AbstractFloatVector {

    override fun toMutable(): AbstractMutableFloatVector {
        return this
    }


}
