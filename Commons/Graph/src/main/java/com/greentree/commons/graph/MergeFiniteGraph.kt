package com.greentree.commons.graph

class MergeFiniteGraph<out V : Any>(
	private val origins: Sequence<FiniteGraph<V>>,
) : FiniteGraph<V> {

	@SafeVarargs
	constructor(vararg base: FiniteGraph<V>) : this(sequenceOf(*base))

	override fun getAdjacencySequence(v: @UnsafeVariance V) = origins.flatMap { it.getAdjacencySequence(v) }

	override fun iterator() = origins.flatten().distinct().iterator()
}

operator fun <V : Any> FiniteGraph<V>.plus(other: FiniteGraph<V>) = MergeFiniteGraph(this, other)
