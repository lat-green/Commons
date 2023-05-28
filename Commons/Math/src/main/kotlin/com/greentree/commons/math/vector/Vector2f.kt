package com.greentree.commons.math.vector

data class Vector2f(override var x: Float = 0f, override var y: Float = 0f) : AbstractMutableVector2f {

    constructor(other: AbstractVector2f) : this(other.x, other.y)
    constructor(value: Float) : this(value, value)

}