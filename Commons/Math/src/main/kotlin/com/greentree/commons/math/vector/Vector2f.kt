package com.greentree.commons.math.vector

class Vector2f(override var x: Float = 0f, override var y: Float = 0f) : AbstractMutableVector2f {

    constructor(other: AbstractMutableVector2f) : this(other.x , other.y)
    constructor(value: Float) : this(value, value)

}