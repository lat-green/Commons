package com.greentree.commons.graph

import com.greentree.commons.graph.algorithm.walk.BFSWalker
import com.greentree.commons.graph.algorithm.walk.GraphWalker
import java.io.Serializable
import java.util.*

interface Graph<out V : Any> : Serializable {

	fun getAdjacencySequence(v: @UnsafeVariance V): Sequence<V>

	@Deprecated("use getAdjacencySequence", ReplaceWith("this.getAdjacencySequence(v)"))
	fun getAdjacencyIterable(v: @UnsafeVariance V): Iterable<V> = getAdjacencySequence(v).asIterable()

	fun walker(): GraphWalker<V> {
		return BFSWalker(this)
	}

	/** @return is v the vertex of this graph
	 */
	operator fun contains(v: @UnsafeVariance V): Boolean

	/** @return is {from, to} the arc of this graph
	 */
	fun has(from: @UnsafeVariance V, to: @UnsafeVariance V): Boolean {
		if(!contains(from)) return false
		return getAdjacencyCollection(from).contains(to)
	}

	fun inverse(): Graph<V> = InverseGraph(this)
}

fun <V : Any> Graph<V>.getAdjacencyCollection(v: V): Collection<V> {
	val result = ArrayList<V>()
	for(e in getAdjacencySequence(v)) result.add(e)
	return Collections.unmodifiableCollection(result)
}

