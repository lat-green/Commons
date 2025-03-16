package com.greentree.commons.geometry.geom3d.mesh

import com.greentree.commons.geometry.geom3d.IShape3D
import com.greentree.commons.geometry.geom3d.face.Face
import com.greentree.commons.geometry.geom3d.mesh.Mesh.Builder.*
import org.joml.Vector3fc

class Mesh(
	vetrex: List<Vector3fc>,
	facesC: List<int3>,
) : IShape3D {

	private val faces: Array<IndexFace>
	private val vetrex: Array<Vector3fc> = vetrex.toTypedArray()

	init {
		this.faces = Array(facesC.size) {
			val f = facesC[it]
			IndexFace(f.a, f.b, f.c)
		}
	}

	fun get(): FloatArray {
		val res = FloatArray(3 * 3 * faces.size)
		var i = 0
		while(i < faces.size) {
			val f = faces[i]
			val i9 = 9 * i
			res[i9] = f.p1.x()
			res[i9 + 1] = f.p1.y()
			res[i9 + 2] = f.p1.z()
			res[i9 + 3] = f.p2.x()
			res[i9 + 4] = f.p2.y()
			res[i9 + 5] = f.p2.z()
			res[i9 + 6] = f.p3.x()
			res[i9 + 7] = f.p3.y()
			res[i9 + 8] = f.p3.z()
			i++
			i++
		}
		return res
	}

	override fun getFaces() = faces

	class Builder {

		private val vertices: MutableList<Vector3fc>
		private val faces: MutableList<int3>

		init {
			faces = ArrayList()
			vertices = ArrayList()
		}

		fun addFaces(face: int3) {
			faces.add(face)
		}

		fun addVertices(vector3f: Vector3fc) {
			vertices.add(vector3f)
		}

		fun build(): Mesh {
			return Mesh(vertices, faces)
		}

		class int3(val a: Int, val b: Int, val c: Int)
	}

	inner class IndexFace(a: Int, b: Int, c: Int) : Face {

		private val vertexIndices = IntArray(3)

		init {
			vertexIndices[0] = a
			vertexIndices[1] = b
			vertexIndices[2] = c
		}

		override val p1: Vector3fc
			get() = vetrex[vertexIndices[0]]
		override val p2: Vector3fc
			get() = vetrex[vertexIndices[1]]
		override val p3: Vector3fc
			get() = vetrex[vertexIndices[2]]
	}
}
