package com.greentree.commons.math.vector

data class Vector2f(override var x: Float = 0f, override var y: Float = 0f) : AbstractMutableVector2f {

    constructor(other: AbstractVector2f) : this(other.x, other.y)
    constructor(value: Float) : this(value, value)

    operator fun plusAssign(other: AbstractVector2f) {
        x += other.x
        y += other.y
    }

    operator fun divAssign(other: AbstractVector2f) {
        x /= other.x
        y /= other.y
    }

    override operator fun divAssign(other: Float) {
        x /= other
        y /= other
    }

}