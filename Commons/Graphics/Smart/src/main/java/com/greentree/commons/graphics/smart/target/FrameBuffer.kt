package com.greentree.commons.graphics.smart.target

import com.greentree.commons.graphics.smart.texture.Texture
import com.greentree.commons.image.PixelFormat

interface FrameBuffer : RenderTarget {

	fun getColorTexture(index: Int): Texture
	fun getColorTexture() = getColorTexture(0)
	fun getDepthTexture(): Texture

	interface Builder {

		fun addColor2D(format: PixelFormat = PixelFormat.RGB): Builder
		fun addColorCubeMap(format: PixelFormat = PixelFormat.RGB): Builder

		fun addDepth(): Builder
		fun addDepthCubeMapTexture(): Builder
		fun addDepthTexture(): Builder

		fun build(width: Int, height: Int): FrameBuffer
	}
}
