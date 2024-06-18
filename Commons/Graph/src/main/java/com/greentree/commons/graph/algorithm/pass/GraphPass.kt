package com.greentree.commons.graph.algorithm.pass

import com.greentree.commons.graph.FiniteGraph
import com.greentree.commons.graph.Graph
import com.greentree.commons.graph.algorithm.walk.path.PathVisitor
import com.greentree.commons.graph.algorithm.walk.vertex.VertexVisitor

interface GraphPass<out V : Any> {

	fun visit(graph: Graph<@UnsafeVariance V>, start: @UnsafeVariance V, visitor: PathVisitor<V>)

	fun visit(graph: Graph<@UnsafeVariance V>, start: @UnsafeVariance V, visitor: VertexVisitor<V>)

	fun visit(graph: FiniteGraph<@UnsafeVariance V>, visitor: PathVisitor<V>) {
		for(v in graph)
			visit(graph, v, visitor)
	}

	fun visit(graph: FiniteGraph<@UnsafeVariance V>, visitor: VertexVisitor<V>) {
		for(v in graph)
			visit(graph, v, visitor)
	}
}

fun <V : Any> Graph<V>.visit(pass: GraphPass<V>, start: V, visitor: PathVisitor<V>) = pass.visit(this, start, visitor)
fun <V : Any> Graph<V>.visit(pass: GraphPass<V>, start: V, visitor: VertexVisitor<V>) = pass.visit(this, start, visitor)

fun <V : Any> FiniteGraph<V>.visit(pass: GraphPass<V>, visitor: PathVisitor<V>) = pass.visit(this, visitor)
fun <V : Any> FiniteGraph<V>.visit(pass: GraphPass<V>, visitor: VertexVisitor<V>) = pass.visit(this, visitor)
