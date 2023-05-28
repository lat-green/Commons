package com.greentree.commons.math.vector

class FinalVector3f(override val x: Float = 0f, override val y: Float = 0f, override val z: Float = 0f) : AbstractVector3f {

    constructor(xyz: AbstractVector3f) : this(xyz.x , xyz.y, xyz.z)
    constructor(value: Float) : this(value, value, value)

}