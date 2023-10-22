package com.greentree.commons.graphics.smart.scene

import com.greentree.commons.graphics.smart.shader.Shader
import com.greentree.commons.graphics.smart.shader.material.Material
import com.greentree.commons.graphics.smart.target.FrameBuffer
import com.greentree.commons.graphics.smart.target.RenderCommandBuffer
import com.greentree.commons.graphics.smart.texture.Texture
import com.greentree.commons.math.Mathf
import org.joml.Matrix3f
import org.joml.Matrix4f
import org.joml.Matrix4fc

interface Camera : HasPosition, SceneObject {

	val skybox: Texture?
	val cameraTarget: CameraFrameBuffer
		get() = CameraFrameBuffer(this, target)
	val target: FrameBuffer
	val width: Float
	val height: Float
	val projection: Matrix4fc
}

class CameraFrameBuffer(
	private val camera: Camera,
	private val origin: FrameBuffer,
) : FrameBuffer by origin {

	override fun buffer() = CameraRenderCommandBuffer(camera, origin.buffer())
}

class CameraRenderCommandBuffer(
	val camera: Camera,
	private val origin: RenderCommandBuffer,
) : RenderCommandBuffer by origin {

	override fun bindMaterial(material: Material) {
		val material = material.newMaterial()
		val view = camera.view
		val projection = camera.projection
		val viewProjection: Matrix4f = Matrix4f().identity().mul(projection).mul(view)
		material.put("projectionView", viewProjection)
		material.put("viewPos", camera.position)
		origin.bindMaterial(material)
	}

	fun bindSkyBoxMaterial(shader: Shader, texture: Texture) {
		var veiwProjection = run {
			val w = camera.width
			val h = camera.height
			val wh = w / h
			Matrix4f().perspective(Mathf.PIHalf / wh, wh, 0f, 1f)
		}
		val mat33 = Matrix3f()
		veiwProjection.mul(Matrix4f(camera.view.get3x3(mat33)))
		val properties = shader.newMaterial()
		properties.put("skybox", texture)
		properties.put("projectionView", veiwProjection)
		properties.put("viewPos", camera.position)
		origin.bindMaterial(properties)
	}
}

