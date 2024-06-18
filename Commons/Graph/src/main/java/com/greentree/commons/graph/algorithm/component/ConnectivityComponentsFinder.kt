package com.greentree.commons.graph.algorithm.component

interface ConnectivityComponentsFinder<out V> {

	fun getComponent(vertex: @UnsafeVariance V): Int {
		val cs = components
		for(i in cs.indices) {
			val c = cs[i]
			if(c.contains(vertex)) return i
		}
		throw IllegalArgumentException("not vertex")
	}

	val components: List<Collection<V>>
}
