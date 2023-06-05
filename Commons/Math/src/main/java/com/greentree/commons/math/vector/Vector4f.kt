package com.greentree.commons.math.vector

data class Vector4f(override var x: Float, override var y: Float, override var z: Float, override var w: Float) :
	AbstractMutableVector4f {

	constructor(xyzw: Float) : this(xyzw, xyzw, xyzw, xyzw)
	constructor() : this(0f)
}