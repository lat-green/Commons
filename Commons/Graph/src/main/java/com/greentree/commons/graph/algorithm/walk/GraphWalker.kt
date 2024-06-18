package com.greentree.commons.graph.algorithm.walk

import com.greentree.commons.graph.FiniteGraph
import com.greentree.commons.graph.algorithm.walk.path.PathToVertexVisitor
import com.greentree.commons.graph.algorithm.walk.path.PathVisitor
import com.greentree.commons.graph.algorithm.walk.vertex.VertexVisitor

interface GraphWalker<out V : Any> {

	fun visit(visitor: VertexVisitor<V>) {
		for(v in finiteGraph!!)
			visit(v, visitor)
	}

	fun visit(visitor: PathVisitor<V>) {
		for(v in finiteGraph!!)
			visit(v, visitor)
	}

	val finiteGraph: FiniteGraph<V>?
		get() = null

	fun visit(start: @UnsafeVariance V, visitor: VertexVisitor<V>)

	fun visit(start: @UnsafeVariance V, visitor: PathVisitor<V>) {
		visit(start, PathToVertexVisitor(visitor))
	}
}
