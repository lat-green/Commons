package com.greentree.commons.graph.algorithm.path

import com.greentree.commons.graph.DirectedArc

data class VertexPath<out V : Any> @JvmOverloads constructor(
	val list: List<V>,
	val distance: (@UnsafeVariance V, @UnsafeVariance V) -> Number = GraphPathBaseDistance.instance(),
) : List<V> by list {

	constructor(begin: V, path: Iterable<V>) : this(listOf(begin) + path)

	fun length(): Double {
		var dis = 0.0
		var p = get(0)
		for(i in 1 until size) {
			val v = get(i)
			dis += distance(p, v).toDouble()
			p = v
		}
		return dis
	}

	fun toJoints(): List<DirectedArc<V>> {
		val res: MutableList<DirectedArc<V>> = ArrayList()
		var v1 = get(0)
		for(i in 1 until size) {
			val v2 = get(i)
			res.add(DirectedArc(v1, v2))
			v1 = v2
		}
		return res
	}
}
