package com.greentree.commons.graph.algorithm.component

import com.greentree.commons.graph.FiniteGraph
import com.greentree.commons.graph.algorithm.walk.vertex.VertexVisitor

class ConnectivityComponentsFinderImpl<V : Any>(private val graph: FiniteGraph<V>) :
	ConnectivityComponentsFinder<V> {

	override val components: List<Collection<V>>
		get() {
			val walker = graph.walker()
			val all = HashSet<V>()
			val c = HashSet<V>()
			val cc = ArrayList<ArrayList<V>>()
			val visitor = VertexVisitor { v: V ->
				c.add(v)
				all.add(v)
				true
			}
			for(v in graph)
				if(!all.contains(v)) {
					walker.visit(v, visitor)
					cc.add(ArrayList(c))
					c.clear()
				}
			return cc
		}
}
