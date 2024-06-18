package com.greentree.commons.graph.algorithm.sort

import com.greentree.commons.graph.FiniteGraph

interface TopologicalSorter<out V> {

	fun sort(list: MutableList<in V>)
}

val <V : Any> FiniteGraph<V>.topologicalSort: List<V>
	get() = getTopologicalSort(ArrayList())

fun <V : Any, L : MutableList<in V>> FiniteGraph<V>.getTopologicalSort(result: L): L {
	topologicalSorter.sort(result)
	return result
}