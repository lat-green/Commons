package com.greentree.commons.graph.algorithm.cycle

import com.greentree.commons.graph.FiniteGraph

class SmartCycleFinder<V : Any>(graph: FiniteGraph<V>) {

	private val res: MutableList<JointCycle<V>> = ArrayList()

	init {
		val color: MutableMap<V, Boolean> = HashMap()
		for(v in graph) color[v] = false
		for(v in graph) {
			dfs(graph, HashMap(), color, v)
			break
		}
		res.removeIf { e: JointCycle<V> -> (e.size < 3) }
	}

	fun get(): List<JointCycle<V>> {
		return res
	}

	private fun dfs(graph: FiniteGraph<V>, parent: MutableMap<V?, V>, color: MutableMap<V, Boolean>, v: V) {
		color[v] = true
		for(to in graph.getAdjacencyIterable(v)) if(!color[to]!!) {
			parent[to] = v
			dfs(graph, parent, color, to)
		} else {
			val cycle: MutableList<V> = ArrayList()
			var t: V = v
			while(true) {
				cycle.add(t)
				if(t == to) break
				t = parent[t]!!
			}
			val c0 = JointCycle(cycle)
			if(!res.contains(c0)) res.add(c0)
		}
		color[v] = false
	}
}
