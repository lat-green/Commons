package com.greentree.commons.graphics.smart.mesh

interface Mesh {

	fun remove(location: Int)

	fun has(location: Int): Boolean

	fun add(location: Int, vbo: VideoBuffer, size: Int, stride: Int, offset: Int, divisor: Int)

	fun vbo(location: Int): VideoBuffer

	interface Builder {

		fun build(): Mesh

		fun add(location: Int, vbo: VideoBuffer, size: Int, stride: Int, offset: Int, divisor: Int)
	}
}

fun Mesh.add(location: Int, vbo: VideoBuffer, size: Int) = add(location, vbo, size, size * vbo.elementSize, 0, 0)
fun Mesh.Builder.add(location: Int, vbo: VideoBuffer, size: Int) =
	add(location, vbo, size, size * vbo.elementSize, 0, 0)


