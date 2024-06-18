package com.greentree.commons.graph.algorithm.walk

import com.greentree.commons.graph.FiniteGraph
import com.greentree.commons.graph.algorithm.walk.vertex.VertexVisitor

interface FiniteGraphWalker<out V : Any> : GraphWalker<V> {

	override fun visit(visitor: VertexVisitor<V>) {
		for(v in finiteGraph)
			visit(v, visitor)
	}

	override val finiteGraph: FiniteGraph<V>
}
