package com.greentree.commons.graph

interface MutableFiniteGraph<V : Any> : FiniteGraph<V> {

	fun clear()

	fun remove(v: V): Boolean

	fun remove(from: V, to: V): Boolean

	fun addAll(graph: FiniteGraph<V>) {
		for(v in graph) {
			add(v)
			for(to in graph.getAdjacencyIterable(v)) {
				add(v, to)
			}
		}
	}

	fun add(v: V)

	fun add(from: V, to: V): Boolean
}
