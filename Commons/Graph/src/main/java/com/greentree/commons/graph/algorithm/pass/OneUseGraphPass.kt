package com.greentree.commons.graph.algorithm.pass

import com.greentree.commons.graph.FiniteGraph
import com.greentree.commons.graph.Graph
import com.greentree.commons.graph.algorithm.walk.asOneUse
import com.greentree.commons.graph.algorithm.walk.path.PathVisitor
import com.greentree.commons.graph.algorithm.walk.vertex.VertexVisitor

class OneUseGraphPass<out V : Any>(
	private val origin: GraphPass<V>,
) : GraphPass<V> {

	override fun visit(graph: Graph<@UnsafeVariance V>, start: @UnsafeVariance V, visitor: VertexVisitor<V>) {
		origin.visit(graph, start, visitor.asOneUse())
	}

	override fun visit(graph: Graph<@UnsafeVariance V>, start: @UnsafeVariance V, visitor: PathVisitor<V>) {
		origin.visit(graph, start, visitor.asOneUse())
	}

	override fun visit(graph: FiniteGraph<@UnsafeVariance V>, visitor: PathVisitor<V>) {
		origin.visit(graph, visitor.asOneUse())
	}

	override fun visit(graph: FiniteGraph<@UnsafeVariance V>, visitor: VertexVisitor<V>) {
		origin.visit(graph, visitor.asOneUse())
	}

}

fun <V : Any> GraphPass<V>.asOneUse() = OneUseGraphPass(this)