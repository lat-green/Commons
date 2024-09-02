package com.greentree.commons.graph.algorithm.pass

import com.greentree.commons.graph.Graph
import com.greentree.commons.graph.algorithm.walk.path.PathVisitor
import java.util.*
import kotlin.collections.AbstractList

class BFSGraphPass<out V : Any> private constructor() : AbstractBFSGraphPass<V> {

	companion object {

		private val INSTANCE = BFSGraphPass<Any>()

		fun <V : Any> instance() = INSTANCE as BFSGraphPass<V>
	}

	override fun visit(graph: Graph<@UnsafeVariance V>, start: @UnsafeVariance V, visitor: PathVisitor<V>) {
		val queue: Queue<List<V>> = LinkedList()

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
}

class PlusList<out E>(
	val list: List<E>,
	val element: E,
) : AbstractList<E>() {

	override val size = list.size + 1

	override fun get(index: Int): E {
		if(index == list.size)
			return element
		return list[index]
	}

	override fun contains(element: @UnsafeVariance E) = element == this.element || element in list
}
