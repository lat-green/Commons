package com.greentree.commons.graphics.smart.shader.material

interface Material : AbstractMaterial {

	fun copy(): Material

	fun newMaterial(): Material
}
