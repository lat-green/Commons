package com.greentree.commons.graph.algorithm.walk

import com.greentree.commons.graph.FiniteGraph
import com.greentree.commons.graph.Graph
import com.greentree.commons.graph.algorithm.walk.vertex.VertexVisitor

class DFSWalker<V : Any>(val graph: Graph<V>) : GraphWalker<V> {

	override val finiteGraph: FiniteGraph<V>?
		get() = if(graph is FiniteGraph<V>) graph else null

	override fun visit(start: V, visitor: VertexVisitor<V>) {
		val used = HashSet<V>()
		dfs(used, visitor, start)
	}

	private fun dfs(used: MutableCollection<in V>, visitor: VertexVisitor<V>, v: V) {
		if(visitor.startVisit(v)) visit(used, visitor, v)
		visitor.endVisit(v)
	}

	private fun dfs(used: MutableCollection<in V>, visitor: VertexVisitor<V>, p: V, v: V) {
		if(visitor.startVisit(p, v)) visit(used, visitor, v)
		visitor.endVisit(p, v)
	}

	private fun visit(used: MutableCollection<in V>, visitor: VertexVisitor<V>, v: V) {
		used.add(v)
		for(to in graph.getAdjacencyIterable(v)) if(!used.contains(to)) dfs(used, visitor, v, to)
		used.remove(v)
	}
}

class DFSFiniteWalker<V : Any>(override val finiteGraph: FiniteGraph<V>) : FiniteGraphWalker<V> {

	override fun visit(start: V, visitor: VertexVisitor<V>) {
		val used = HashSet<V>()
		dfs(used, visitor, start)
	}

	private fun dfs(used: MutableCollection<in V>, visitor: VertexVisitor<V>, v: V) {
		if(visitor.startVisit(v)) visit(used, visitor, v)
		visitor.endVisit(v)
	}

	private fun dfs(used: MutableCollection<in V>, visitor: VertexVisitor<V>, p: V, v: V) {
		if(visitor.startVisit(p, v)) visit(used, visitor, v)
		visitor.endVisit(p, v)
	}

	private fun visit(used: MutableCollection<in V>, visitor: VertexVisitor<V>, v: V) {
		used.add(v)
		for(to in finiteGraph.getAdjacencyIterable(v)) if(!used.contains(to)) dfs(used, visitor, v, to)
		used.remove(v)
	}
}
