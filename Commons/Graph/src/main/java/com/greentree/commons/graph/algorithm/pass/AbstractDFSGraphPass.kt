package com.greentree.commons.graph.algorithm.pass

import com.greentree.commons.graph.FiniteGraph
import com.greentree.commons.graph.Graph
import com.greentree.commons.graph.algorithm.walk.path.PathToVertexVisitor
import com.greentree.commons.graph.algorithm.walk.path.PathVisitor
import com.greentree.commons.graph.algorithm.walk.vertex.VertexVisitor

interface AbstractDFSGraphPass<out V : Any> : GraphPass<V> {

	override fun visit(graph: Graph<@UnsafeVariance V>, start: @UnsafeVariance V, visitor: PathVisitor<V>) {
		visit(graph, start, PathToVertexVisitor(visitor))
	}

	override fun visit(graph: Graph<@UnsafeVariance V>, start: @UnsafeVariance V, visitor: VertexVisitor<V>)

	override fun visit(graph: FiniteGraph<@UnsafeVariance V>, visitor: PathVisitor<V>) {
		visit(graph, PathToVertexVisitor(visitor))
	}
}