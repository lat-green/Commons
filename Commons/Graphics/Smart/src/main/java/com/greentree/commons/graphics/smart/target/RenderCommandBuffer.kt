package com.greentree.commons.graphics.smart.target

import com.greentree.commons.graphics.smart.mesh.Mesh
import com.greentree.commons.graphics.smart.shader.material.Material
import com.greentree.commons.image.Color

interface RenderCommandBuffer {

	fun clear()

	fun clear(color: Color, depth: Float) {
		clearColor(color)
		clearDepth(depth)
	}

	fun clearColor(color: Color)

	fun clearDepth(depth: Float)

	fun disableCullFace()

	fun disableDepthTest()

	fun bindMesh(mesh: Mesh)

	fun bindMaterial(material: Material)

	fun draw(count: Int = 1)

	fun enableCullFace()

	fun enableDepthTest()

	fun execute()
}
