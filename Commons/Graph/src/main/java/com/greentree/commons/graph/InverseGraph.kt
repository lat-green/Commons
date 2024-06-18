package com.greentree.commons.graph

@Deprecated("")
data class InverseGraph<out V : Any>(
	val origin: Graph<V>,
) : Graph<V> {

	override fun getAdjacencySequence(v: @UnsafeVariance V): Sequence<V> {
		TODO("Not yet implemented")
	}

	override fun has(from: @UnsafeVariance V, to: @UnsafeVariance V) = origin.has(to, from)

	override fun contains(v: @UnsafeVariance V) = v in origin
}

data class InverseFiniteGraph<out V : Any>(
	val origin: FiniteGraph<V>,
) : FiniteGraph<V> {

	override fun getAdjacencySequence(v: @UnsafeVariance V) =
		origin.asSequence().filter { v in origin.getAdjacencySequence(it) }

	override fun has(from: @UnsafeVariance V, to: @UnsafeVariance V) = origin.has(to, from)

	override fun contains(v: @UnsafeVariance V) = v in origin

	override fun iterator() = origin.iterator()
}