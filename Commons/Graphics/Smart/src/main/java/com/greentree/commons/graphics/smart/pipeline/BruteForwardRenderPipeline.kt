package com.greentree.commons.graphics.smart.pipeline

import com.greentree.commons.graphics.smart.RenderContext
import com.greentree.commons.graphics.smart.mesh.Mesh
import com.greentree.commons.graphics.smart.scene.RenderScene
import com.greentree.commons.graphics.smart.scene.get
import com.greentree.commons.graphics.smart.scene.projection
import com.greentree.commons.graphics.smart.scene.view
import com.greentree.commons.graphics.smart.shader.material.Material
import com.greentree.commons.graphics.smart.target.FrameBuffer
import org.joml.Matrix4f
import org.joml.Vector3f

class BruteForwardRenderPipeline(private val scene: RenderScene, private val context: RenderContext) : RenderPipeline {

	private val SUPER_DIRECTION_MATERIAL: Material = context.getDefaultShadowShader().newMaterial()
	private val SUPER_POINT_SHADOW: Material = context.getDefaultCubeMapShadowShader().newMaterial()
	private val POINT_SHADOW: Array<Material>
	private val SKYBOX_MESH: Mesh

	init {
		mapShadowMaterial(SUPER_DIRECTION_MATERIAL)
		mapShadowMaterial(SUPER_POINT_SHADOW)

		POINT_SHADOW = Array(shadowMatrices.size) {
			val shadow = SUPER_POINT_SHADOW.newMaterial()
			shadow.put("face", it)
			shadow.put("projectionView", shadowMatrices[it])
			shadow
		}
		SKYBOX_MESH = context.getDefaultMeshBox()
	}

	override fun execute() {
		scene.run {
			for(light in pointLights)
				if(light.hasShadow) {
					SUPER_POINT_SHADOW.put("lightPos", light.position)
					val target = light.options["depth"].get {
						context.newFrameBuffer().addDepthCubeMapTexture().build(SHADOW_SIZE, SHADOW_SIZE)
					}
					val buffer = target.buffer()
					buffer.clearDepth(1f)
					buffer.enableCullFace()
					buffer.enableDepthTest()
					for(m in models) {
						buffer.bindMesh(m.mesh)
						SUPER_POINT_SHADOW.put("model", m.model)
						for(properties in POINT_SHADOW) {
							buffer.bindMaterial(properties.copy())
							buffer.draw()
						}
					}
					buffer.execute()
					buffer.clear()
				}
		}
		scene.run {
			for(light in directionLights)
				if(light.hasShadow) {
					val target = light.options["depth"].get {
						context.newFrameBuffer().addDepthTexture().build(SHADOW_SIZE, SHADOW_SIZE)
					}
					val buffer = target.buffer()
					buffer.clearDepth(1f)
					buffer.enableCullFace()
					buffer.enableDepthTest()
					for(m in models) {
						val mesh = m.mesh
						buffer.bindMesh(mesh)
						val properties = SUPER_DIRECTION_MATERIAL.newMaterial()
						properties.put("model", m.model)
						buffer.bindMaterial(properties)
						buffer.draw()
					}
					buffer.execute()
					buffer.clear()
				}
		}
		scene.run {
			for(camera in cameras) {
				val target = camera.cameraTarget
				val buffer = target.buffer()
				buffer.clearDepth(1f)
				val skybox = camera.skybox
				if(skybox != null) {
					buffer.bindMesh(SKYBOX_MESH)
					buffer.bindSkyBoxMaterial(context.getDefaultSkyBoxShader(), skybox)
					buffer.draw()
				}
				buffer.enableCullFace()
				buffer.enableDepthTest()
				for(materialGroups in materials) {
					val material = materialGroups.value.newMaterial()
					mapMaterial(material)
					for(meshGroups in materialGroups) {
						buffer.bindMesh(meshGroups.value)
						for(modelMatrix in meshGroups) {
							val properties = material.newMaterial()
							properties.put("model", modelMatrix)
							buffer.bindMaterial(properties)
							buffer.draw()
						}
					}
				}
				buffer.execute()
				buffer.clear()
			}
		}
	}

	private fun mapMaterial(properties: Material) {
		mapMaterial0(properties)
		scene.run {
			var i = 0
			for(light in pointLights) {
				val name = "point_light[$i]."
				properties.put(name + "position", light.position)
				properties.putRGB(name + "color", light.color)
				properties.put(name + "intensity", light.intensity)
				if(light.hasShadow)
					properties.put(name + "depth", light.options["depth"].get<FrameBuffer>().getDepthTexture())
				i++
			}
			properties.put("count_point_light", i)
		}
		scene.run {
			var i = 0
			for(light in directionLights) {
				val name = "dir_light[$i]."
				properties.put(name + "direction", light.direction)
				properties.putRGB(name + "color", light.color)
				properties.put(name + "intensity", light.intensity)
				if(light.hasShadow) {
					properties.put("lightSpaceMatrix[$i]", Matrix4f().mul(light.projection).mul(light.view))
					properties.put(name + "depth", light.options["depth"].get<FrameBuffer>().getDepthTexture())
				}
				i++
			}
			properties.put("count_dir_light", i)
		}
	}
}

private const val SHADOW_SIZE = 256 * 4 * 4
private const val FAR_PLANE = 250f

private fun mapShadowMaterial(properties: Material) {
	mapMaterial0(properties)
}

private fun mapMaterial0(properties: Material) {
	properties.put("far_plane", FAR_PLANE)
}

private val shadowMatrices by lazy {
	val zeroVector = Vector3f()
	val shadowMatrices = arrayOfNulls<Matrix4f>(6)
	shadowMatrices[0] = Matrix4f().lookAt(
		zeroVector, Vector3f(1.0f, 0.0f, 0.0f),
		Vector3f(0.0f, -1.0f, 0.0f)
	)
	shadowMatrices[1] = Matrix4f().lookAt(
		zeroVector, Vector3f(-1.0f, 0.0f, 0.0f),
		Vector3f(0.0f, -1.0f, 0.0f)
	)
	shadowMatrices[2] = Matrix4f().lookAt(
		zeroVector, Vector3f(0.0f, 1.0f, 0.0f),
		Vector3f(0.0f, 0.0f, 1.0f)
	)
	shadowMatrices[3] = Matrix4f().lookAt(
		zeroVector, Vector3f(0.0f, -1.0f, 0.0f),
		Vector3f(0.0f, 0.0f, -1.0f)
	)
	shadowMatrices[4] = Matrix4f().lookAt(
		zeroVector, Vector3f(0.0f, 0.0f, 1.0f),
		Vector3f(0.0f, -1.0f, 0.0f)
	)
	shadowMatrices[5] = Matrix4f().lookAt(
		zeroVector, Vector3f(0.0f, 0.0f, -1.0f),
		Vector3f(0.0f, -1.0f, 0.0f)
	)
	run {
		val shadowProj = Matrix4f(1f, 0f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 0f, -1f, 1f, 0f, 0f, 0f, 0f)
		for(i in 0 .. 5) shadowProj.mul(shadowMatrices[i], shadowMatrices[i])
	}
	shadowMatrices as Array<Matrix4f>
}