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

@JvmName("topologicalSort_Function")
fun <T : Any> Sequence<T>.topologicalSort(adjacency: (T) -> Sequence<T>): List<T> {
	val list = this
	return object : FiniteGraph<T> {
		override fun getAdjacencySequence(v: T) = adjacency(v)

		override fun iterator() = list.iterator()
	}.topologicalSort
}

fun <T : Any> Sequence<T>.topologicalSort(adjacency: (v: T, to: T) -> Boolean): List<T> {
	val list = this
	return object : FiniteGraph<T> {
		override fun getAdjacencySequence(v: T) = sequence {
			for(to in list)
				if(adjacency(v, to))
					yield(to)
		}

		override fun iterator() = list.iterator()
	}.topologicalSort
}