package com.greentree.commons.graph.algorithm.pass

import com.greentree.commons.graph.Graph
import com.greentree.commons.graph.algorithm.walk.vertex.VertexVisitor

class DFSGraphPass<out V : Any> private constructor() : AbstractDFSGraphPass<V> {

	companion object {

		private val INSTANCE = DFSGraphPass<Any>()

		fun <V : Any> instance() = INSTANCE as DFSGraphPass<V>
	}

	override fun visit(graph: Graph<@UnsafeVariance V>, start: @UnsafeVariance V, visitor: VertexVisitor<V>) {
		if(visitor.startVisit(start)) {
			for(to in graph.getAdjacencySequence(start))
				dfs(graph, start, to, visitor)
			visitor.endVisit(start)
		}
	}

	private fun dfs(
		graph: Graph<@UnsafeVariance V>,
		parent: @UnsafeVariance V,
		v: @UnsafeVariance V,
		visitor: VertexVisitor<V>,
	) {
		if(visitor.startVisit(parent, v)) {
			for(to in graph.getAdjacencySequence(v))
				dfs(graph, v, to, visitor)
			visitor.endVisit(parent, v)
		}
	}
}