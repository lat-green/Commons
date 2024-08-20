package com.greentree.commons.graph.algorithm.path

import com.greentree.commons.graph.FiniteGraph
import java.util.*

object AStarPathFinder {

	fun <V : Any> get(
		g: FiniteGraph<V>,
		arcLength: (V, V) -> Number,
		approximateDisToEnd: (V) -> Number,
		begin: V,
		end: V,
	): VertexPath<V>? {
		val queue = PriorityQueue<Pair<Double, V>>(Comparator.comparing { p -> p.first })
		queue.add(Pair(0.0, begin))
		val parent: MutableMap<V, V> = HashMap()
		val disToStart: MutableMap<V, Double> = HashMap()
		for(v in g) disToStart[v] = Double.MAX_VALUE
		disToStart[begin] = 0.0
		while(queue.isNotEmpty()) {
			val curD: Double
			var v: V?
			val p = queue.remove()
			curD = p.first
			v = p.second
			if(v == end) {
				val res: MutableList<V> = ArrayList()
				while(v != null) {
					res.add(v)
					v = parent[v]
				}
				res.reverse()
				return VertexPath(res)
			}
			if(curD > disToStart[v]!!) continue
			for(to in g.getAdjacencySequence(v)) {
				val len = arcLength(v, to).toDouble()
				val dis = (len + approximateDisToEnd(to).toDouble() + disToStart[v]!!)
				if(dis < disToStart[to]!!) {
					disToStart[to] = dis
					parent[to] = v
					queue.add(Pair(disToStart[to]!!, to))
				}
			}
		}
		return null
	}
}
