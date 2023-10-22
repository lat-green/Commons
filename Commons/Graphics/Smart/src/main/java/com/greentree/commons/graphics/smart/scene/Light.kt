package com.greentree.commons.graphics.smart.scene

import com.greentree.commons.image.Color
import com.greentree.commons.math.vector.AbstractVector3f
import org.joml.Matrix4f

sealed interface Light : SceneObject {

	val hasShadow: Boolean
	val color: Color
	val intensity: Float
}

interface PointLight : Light {

	val position: AbstractVector3f
}

interface DirectionLight : Light, HasPosition {

	val size: Float
}

val DirectionLight.projection: Matrix4f
	get() {
		val min = 1
		val max = 500
		return Matrix4f().ortho(
			-size,
			size,
			-size,
			size,
			min.toFloat(),
			max.toFloat()
		)
	}

