package com.greentree.commons.graph

data class ConstCacheFiniteGraph<V : Any>(
	private val origin: FiniteGraph<V>,
) : FiniteGraph<V> by origin {

	private val cache: MutableMap<V, Sequence<V>> = HashMap()

	override fun getAdjacencySequence(v: V): Sequence<V> {
		return cache.getOrPut(v) {
			origin.getAdjacencySequence(v)
		}
	}
}
