package com.greentree.commons.math.quaternion

data class Quaternion(override var x: Float, override var y: Float, override var z: Float, override var w: Float) :
	AbstractMutableQuaternion {

	constructor() : this(0f, 0f, 0f, 1f)
}