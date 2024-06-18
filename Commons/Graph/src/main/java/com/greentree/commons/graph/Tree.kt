package com.greentree.commons.graph

import com.greentree.commons.graph.algorithm.brige.BridgeFinder
import com.greentree.commons.graph.algorithm.component.ConnectivityComponentsFinder
import com.greentree.commons.graph.algorithm.cycle.CycleFinder
import com.greentree.commons.graph.algorithm.cycle.VertexCycle
import com.greentree.commons.util.collection.EmptyCollection
import com.greentree.commons.util.iterator.IteratorUtil

interface Tree<out V : Any> : FiniteGraph<V>, CycleFinder<V>, BridgeFinder<V> {

	override val bridges: Iterable<DirectedArc<V>>
		get() = IteratorUtil.empty()
	override val cycles: Collection<VertexCycle<V>>
		get() = EmptyCollection()
	override val bridgeFinder: BridgeFinder<V>
		get() = this
	override val cycleFinder: CycleFinder<V>
		get() = this
	override val connectivityComponentsFinder: ConnectivityComponentsFinder<V>
		get() = TreeConnectivityComponentsFinder(this)

	class TreeConnectivityComponentsFinder<out V : Any>(tree: Tree<V>) : ConnectivityComponentsFinder<V> {

		private val tree: Tree<V> = tree

		override fun getComponent(vertex: @UnsafeVariance V): Int {
			if(vertex in tree) return 0
			throw IllegalArgumentException("not vertex")
		}

		override val components: List<Collection<V>>
			get() {
				val result = ArrayList<Collection<V>>()
				val c = ArrayList<V>()
				result.add(c)
				for(v in tree) c.add(v)
				return result
			}
	}
}
