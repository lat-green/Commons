package com.greentree.commons.graph.algorithm.pass

import com.greentree.commons.graph.FiniteGraph
import com.greentree.commons.graph.Graph
import com.greentree.commons.graph.algorithm.walk.path.PathVisitor
import com.greentree.commons.graph.algorithm.walk.path.VertexToPathVisitor
import com.greentree.commons.graph.algorithm.walk.vertex.VertexVisitor

interface AbstractBFSGraphPass<out V : Any> : GraphPass<V> {

	override fun visit(graph: Graph<@UnsafeVariance V>, start: @UnsafeVariance V, visitor: PathVisitor<V>)

	override fun visit(graph: Graph<@UnsafeVariance V>, start: @UnsafeVariance V, visitor: VertexVisitor<V>) {
		visit(graph, start, VertexToPathVisitor(visitor))
	}

	override fun visit(graph: FiniteGraph<@UnsafeVariance V>, visitor: VertexVisitor<V>) {
		visit(graph, VertexToPathVisitor(visitor))
	}
}