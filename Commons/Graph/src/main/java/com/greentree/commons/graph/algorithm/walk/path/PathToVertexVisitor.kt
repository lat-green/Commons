package com.greentree.commons.graph.algorithm.walk.path

import com.greentree.commons.graph.algorithm.walk.vertex.VertexVisitor
import java.util.*

data class PathToVertexVisitor<in V : Any>(
	val origin: PathVisitor<V>,
) : VertexVisitor<V> {

	private val path = Stack<V>()

	override fun startVisit(v: V): Boolean {
		path.add(v)
		return origin.visit(path)
	}

	override fun endVisit(v: V) {
		path.pop()
	}
}