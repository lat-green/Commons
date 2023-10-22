package com.greentree.commons.graphics.smart.scene

import com.greentree.commons.graphics.smart.mesh.Mesh
import com.greentree.commons.graphics.smart.shader.material.Material
import org.joml.Matrix4f

interface Model : SceneObject {

	val mesh: Mesh
	val material: Material
	val model: Matrix4f
}
