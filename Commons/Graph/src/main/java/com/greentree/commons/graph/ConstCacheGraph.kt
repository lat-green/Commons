package com.greentree.commons.graph

data class ConstCacheGraph<V : Any>(
	private val origin: Graph<V>,
) : Graph<V> by origin {

	private val cache: MutableMap<V, Sequence<V>> = HashMap()

	override fun getAdjacencySequence(v: V): Sequence<V> {
		return cache.getOrPut(v) {
			origin.getAdjacencySequence(v)
		}
	}
}
