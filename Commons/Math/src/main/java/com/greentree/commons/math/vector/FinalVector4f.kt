package com.greentree.commons.math.vector

data class FinalVector4f(
    override val x: Float = 0f,
    override val y: Float = 0f,
    override val z: Float = 0f,
    override val w: Float = 0f,
) : AbstractVector4f {

	constructor(xyzw: AbstractVector4f) : this(xyzw.x, xyzw.y, xyzw.z, xyzw.w)
	constructor(value: Float) : this(value, value, value, value)
}