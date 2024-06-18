package com.greentree.commons.graph.algorithm.path

import com.greentree.commons.graph.FiniteGraph
import java.util.*

class DijkstraMinPathFinder<out V : Any> @JvmOverloads constructor(
	val graph: FiniteGraph<V>,
	val start: V,
	val arc_len: (@UnsafeVariance V, @UnsafeVariance V) -> Number = GraphPathBaseDistance.instance(),
) {

	private val p = HashMap<V, V>()

	init {
		val d = HashMap<V, Number?>()
		val q = ArrayList<V>()
		val cmp = Comparator.comparingDouble { n: V ->
			d[n]!!
				.toDouble()
		}
		fill(d, graph, Double.MAX_VALUE)
		d[start] = 0f
		q.add(start)
		while(!q.isEmpty()) {
			val v = q.first()
			q.remove(v)
			val v_len = d[v]!!.toDouble()
			for(to in graph.getAdjacencyIterable(v)) {
				val len = d[to]!!.toDouble()
				val new_len = v_len + arc_len(v, to).toDouble()
				if(new_len < len) {
					q.remove(to)
					d[to] = new_len
					p[to] = v
					val index = Collections.binarySearch(q, to, cmp)
					if(index >= 0) q.add(index, to)
					else q.add(-index - 1, to)
				}
			}
		}
	}

	@Deprecated("", ReplaceWith("iterable.first()"), DeprecationLevel.ERROR)
	private fun <T> get(iterable: Iterable<T>): T? {
		for(v in iterable)
			return v
		return null
	}

	fun get(finish: @UnsafeVariance V): VertexPath<V> {
		var finish = finish
		val path = ArrayList<V>()
		while(finish !== start) {
			path.add(finish)
			finish = p[finish]!!
		}
		Collections.reverse(path)
		return VertexPath<V>(start, path)
	}

	override fun toString(): String {
		return "DijkstraMinPathFinder [$graph]"
	}

	companion object {

		private fun <T> fill(
			map: MutableMap<in T, in Double?>, vertex: Iterable<T>,
			value: Double,
		) {
			val d = value
			for(v in vertex) map[v] = d
		}
	}
}
