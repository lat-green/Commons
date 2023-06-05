package com.greentree.commons.math.vector

data class FinalVector1f(override val x: Float) : AbstractVector1f {

    constructor(x: AbstractVector1f) : this(x.x)

}