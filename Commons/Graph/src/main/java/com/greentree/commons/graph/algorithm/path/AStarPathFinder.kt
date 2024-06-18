package com.greentree.commons.graph.algorithm.path

import com.greentree.commons.graph.FiniteGraph
import com.greentree.commons.util.cortege.Pair
import java.util.*

object AStarPathFinder {

	fun <V : Any> get(
		g: FiniteGraph<V>,
		arc_length: (V, V) -> Number,
		approximate_dis_to_end: (V) -> Number,
		begin: V,
		end: V,
	): VertexPath<V>? {
		val queue = PriorityQueue<Pair<Double, V>>(Comparator.comparing { p -> p.first })
		queue.add(Pair(0.0, begin))
		val parent: MutableMap<V, V> = HashMap()
		val dis_to_start: MutableMap<V, Double> = HashMap()
		for(v in g) dis_to_start[v] = Double.MAX_VALUE
		dis_to_start[begin] = 0.0
		while(!queue.isEmpty()) {
			val cur_d: Double
			var v: V?
			val p = queue.remove()
			cur_d = p.first
			v = p.seconde
			if(v == end) {
				val res: MutableList<V> = ArrayList()
				while(v != null) {
					res.add(v)
					v = parent[v]
				}
				Collections.reverse(res)
				return VertexPath(res)
			}
			if(v != null) {
				if(cur_d > dis_to_start[v]!!) continue
				for(to in g.getAdjacencyIterable(v)) {
					val len = arc_length(v, to).toDouble()
					val dis = (len + approximate_dis_to_end(to).toDouble() + dis_to_start[v]!!)
					if(dis < dis_to_start[to]!!) {
						dis_to_start[to] = dis
						parent[to] = v
						queue.add(Pair(dis_to_start[to], to))
					}
				}
			}
		}
		return null
	}
}
