package com.greentree.commons.graphics.smart.shader

import com.greentree.commons.graphics.smart.shader.material.Material

interface Shader {

	fun newMaterial(): Material

	fun getBlockIndex(name: String): BlockIndex
}
