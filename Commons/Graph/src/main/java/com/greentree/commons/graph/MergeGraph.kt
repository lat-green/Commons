package com.greentree.commons.graph

class MergeGraph<out V : Any>(
	private val origins: Sequence<Graph<V>>,
) : Graph<V> {

	@SafeVarargs
	constructor(vararg base: Graph<V>) : this(sequenceOf(*base))

	override fun getAdjacencySequence(v: @UnsafeVariance V) = origins.flatMap { it.getAdjacencySequence(v) }

	override fun contains(v: @UnsafeVariance V) = origins.any { v in it }
}

operator fun <V : Any> Graph<V>.plus(other: Graph<V>) = MergeGraph(this, other)
