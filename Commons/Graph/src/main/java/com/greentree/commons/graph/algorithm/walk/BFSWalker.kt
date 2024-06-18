package com.greentree.commons.graph.algorithm.walk

import com.greentree.commons.graph.Graph
import com.greentree.commons.graph.algorithm.walk.path.PathVisitor
import com.greentree.commons.graph.algorithm.walk.vertex.VertexVisitor
import com.greentree.commons.graph.algorithm.walk.vertex.visitPath
import java.util.*

data class BFSWalker<V : Any>(private val graph: Graph<V>) : GraphWalker<V> {

	override fun visit(start: V, visitor: VertexVisitor<V>) {
		val queue: Queue<BFSVertexWalkInfo<V>> = LinkedList()
		visit(queue, visitor, BFSVertexWalkInfo(start))
		while(!queue.isEmpty()) {
			val v = queue.remove()
			visit(queue, visitor, v)
		}
	}

	override fun visit(start: V, visitor: PathVisitor<V>) {
		val queue: Queue<List<V>> = LinkedList()
		for(to in graph.getAdjacencySequence(start))
			queue.add(listOf(start, to))

		TODO("Not yet implemented")
	}

	private fun visit(
		queue: Queue<BFSVertexWalkInfo<V>>, visitor: VertexVisitor<V>,
		info: BFSVertexWalkInfo<V>,
	) {
		val v = info.vertex()
		var deadlock = true
		for(to in graph.getAdjacencyIterable(v))
			if(!info.contains(to)) {
				val toInfo = BFSVertexWalkInfo(info, to)
				queue.add(toInfo)
				deadlock = false
			}
		if(!deadlock)
			return
		visitor.visitPath(info.path.iterator())
	}

	private class BFSVertexWalkInfo<V> {

		val path: Stack<V> = Stack()

		private constructor()

		constructor(p: BFSVertexWalkInfo<V>, v: V) {
			path.addAll(p.path)
			path.add(v)
		}

		constructor(v: V) {
			path.add(v)
		}

		fun contains(to: V): Boolean {
			return path.contains(to)
		}

		fun vertex(): V {
			return path.peek()
		}
	}
}
