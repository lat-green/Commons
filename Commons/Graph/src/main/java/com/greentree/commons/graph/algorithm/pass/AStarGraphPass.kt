package com.greentree.commons.graph.algorithm.pass

import com.greentree.commons.graph.Graph
import com.greentree.commons.graph.algorithm.walk.path.PathVisitor
import com.greentree.commons.graph.algorithm.walk.path.VertexToPathVisitor
import com.greentree.commons.graph.algorithm.walk.vertex.VertexVisitor
import java.util.*

data class AStarGraphPass<V : Any>(
	val comparator: Comparator<V>,
) : GraphPass<V> {

	override fun visit(graph: Graph<V>, start: V, visitor: PathVisitor<V>) {
		val queue: Queue<List<V>> = PriorityQueue { a, b -> comparator.compare(a.last(), b.last()) }

		queue.add(listOf(start))

		while(queue.isNotEmpty()) {
			val list = queue.remove()
			if(visitor.visit(list)) {
				val v = list.last()
				for(to in graph.getAdjacencySequence(v))
					if(to !in list)
						queue.add(PlusList(list, to))
			}
		}
	}

	override fun visit(graph: Graph<V>, start: V, visitor: VertexVisitor<V>) {
		visit(graph, start, VertexToPathVisitor(visitor))
	}
}

@JvmName("findMin_Comparable")
fun <V : Comparable<V>> Graph<V>.findMin(start: V): V {
	return findMin(start, Comparator.comparing { it })
}

fun <V : Any> Graph<V>.findMin(start: V, comparator: Comparator<V>): V {
	var result = start

	class FindMinVisitor : VertexVisitor<V> {

		override fun startVisit(v: V): Boolean {
			return true
		}

		override fun startVisit(parent: V, v: V): Boolean {
			if(comparator.compare(parent, v) > 0) {
				result = v
				return true
			} else {
				return false
			}
		}
	}
	visit(
		BFSGraphPass.instance(),
		start,
		FindMinVisitor()
	)
	return result
}