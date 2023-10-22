package com.greentree.commons.graphics.smart

import com.greentree.commons.graphics.smart.mesh.Mesh
import com.greentree.commons.graphics.smart.mesh.VideoBuffer
import com.greentree.commons.graphics.smart.shader.BindingPoint
import com.greentree.commons.graphics.smart.shader.Shader
import com.greentree.commons.graphics.smart.target.FrameBuffer
import java.nio.ByteBuffer

interface RenderContext {

	fun getDefaultCubeMapShadowShader(): Shader
	fun getDefaultShadowShader(): Shader
	fun getDefaultSkyBoxShader(): Shader
	fun getDefaultTextureShader(): Shader
	fun getDefaultSpriteShader(): Shader

	fun getDefaultMeshBox(): Mesh
	fun getDefaultMeshQuad(): Mesh
	fun getDefaultMeshQuadSprite(): Mesh
	fun newMesh(): Mesh.Builder

	fun newBindingPoint(): BindingPoint

	fun newFrameBuffer(): FrameBuffer.Builder

	fun newVideoBuffer(): VideoBuffer.Builder

	fun newStackBuffer(size: Int): StackBuffer
}