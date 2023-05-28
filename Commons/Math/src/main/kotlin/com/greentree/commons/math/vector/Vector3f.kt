package com.greentree.commons.math.vector

data class Vector3f(override var x: Float = 0f, override var y: Float = 0f, override var z: Float = 0f) : AbstractMutableVector3f {

    constructor(xy: AbstractVector2f, z: Float = 0f) : this(xy.x, xy.y, z)
    constructor(xyz: AbstractVector3f) : this(xyz.x, xyz.y, xyz.z)
    constructor(xyz: Float) : this(xyz, xyz, xyz)
}