package com.greentree.commons.graphics.smart.scene

import com.greentree.commons.graphics.smart.mesh.Mesh
import com.greentree.commons.graphics.smart.shader.material.Material
import org.joml.Matrix4f

interface RenderScene {

	val cameras: Iterable<Camera>
	val models: Iterable<Model>
	val pointLights: Iterable<PointLight>
	val directionLights: Iterable<DirectionLight>
	val materials: Iterable<Group<Material, Group<Mesh, Matrix4f>>>
		get() = models.groupBy { it.material }
			.map { (k, v) ->
				GroupImpl(
					k,
					v.size,
					v.groupBy { it.mesh }.map { (k, v) -> GroupImpl(k, v.size, v.map { it.model }) })
			}
}

interface Group<T, I> : Iterable<I> {

	val value: T
	val size: Int
}

data class GroupImpl<T, I>(override val value: T, override val size: Int, private val iterable: Iterable<I>) :
	Group<T, I> {

	override fun iterator() = iterable.iterator()
}