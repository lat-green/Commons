package com.greentree.commons.graphics.smart.scene

import com.greentree.commons.math.vector.AbstractVector3f
import org.joml.Matrix4f
import org.joml.Vector3f

interface HasPosition {

	val position: AbstractVector3f
	val direction: AbstractVector3f
}

private val UP = Vector3f(0f, 1f, 0f)
val HasPosition.view: Matrix4f
	get() {
		return Matrix4f().lookAt(
			position.toJoml(),
			(position + direction).toJoml(),
			UP
		)
	}
