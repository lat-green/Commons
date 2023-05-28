package com.greentree.commons.math.vector

class FinalVector1f(override val x: Float) : AbstractVector1f {

    constructor(x: AbstractVector1f) : this(x.x)

}