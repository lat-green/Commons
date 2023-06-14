package com.greentree.commons.math.quaternion

data class FinalQuaternion(override val x: Float, override val y: Float, override val z: Float, override val w: Float) :
	AbstractQuaternion {

	constructor() : this(0f, 0f, 0f, 1f)
}