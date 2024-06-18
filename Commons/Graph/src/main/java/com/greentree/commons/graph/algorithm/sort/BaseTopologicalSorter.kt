package com.greentree.commons.graph.algorithm.sort

import com.greentree.commons.graph.FiniteGraph
import com.greentree.commons.graph.algorithm.pass.DFSGraphPass
import com.greentree.commons.graph.algorithm.pass.asOneUse
import com.greentree.commons.graph.algorithm.walk.vertex.VertexVisitor

data class BaseTopologicalSorter<V : Any>(val graph: FiniteGraph<V>) : TopologicalSorter<V> {

	override fun sort(list: MutableList<in V>) {
		val walker = DFSGraphPass.instance<V>().asOneUse()
		walker.visit(graph, object : VertexVisitor<V> {
			override fun endVisit(v: V) {
				list.add(v)
			}

			override fun startVisit(v: V): Boolean {
				return true
			}
		})
		list.reverse()
	}
}
