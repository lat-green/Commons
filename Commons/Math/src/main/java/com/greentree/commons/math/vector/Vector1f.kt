package com.greentree.commons.math.vector

data class Vector1f(override var x: Float = 0f) : AbstractMutableVector1f {

    constructor(other: AbstractVector1f) : this(other.x)

}