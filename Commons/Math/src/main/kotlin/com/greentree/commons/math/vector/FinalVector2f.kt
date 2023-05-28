package com.greentree.commons.math.vector

class FinalVector2f(override val x: Float = 0f, override val y: Float = 0f) : AbstractVector2f {

    constructor(other: AbstractVector2f) : this(other.x, other.y)
    constructor(value: Float) : this(value, value)

}