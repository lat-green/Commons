package com.greentree.commons.graph

import com.greentree.commons.graph.algorithm.brige.BridgeFinder
import com.greentree.commons.graph.algorithm.brige.BridgeFinderImpl
import com.greentree.commons.graph.algorithm.component.ConnectivityComponentsFinder
import com.greentree.commons.graph.algorithm.component.ConnectivityComponentsFinderImpl
import com.greentree.commons.graph.algorithm.cycle.CycleFinder
import com.greentree.commons.graph.algorithm.cycle.CycleFinderImpl
import com.greentree.commons.graph.algorithm.path.MiddleMinPathFinder
import com.greentree.commons.graph.algorithm.path.PathFinder
import com.greentree.commons.graph.algorithm.path.min.MinPathFinder
import com.greentree.commons.graph.algorithm.sort.BaseTopologicalSorter
import com.greentree.commons.graph.algorithm.sort.TopologicalSorter
import com.greentree.commons.graph.algorithm.walk.DFSFiniteWalker
import com.greentree.commons.graph.algorithm.walk.FiniteGraphWalker
import com.greentree.commons.util.iterator.IteratorUtil

interface FiniteGraph<out V : Any> : Iterable<V>, Graph<V> {

	val joints: Iterable<DirectedArc<V>>
		get() {
			val result = ArrayList<DirectedArc<V>>()
			for(begin in this) for(end in getAdjacencyIterable(begin)) result.add(DirectedArc(begin, end))
			return result
		}

	override fun walker(): FiniteGraphWalker<V> {
		return DFSFiniteWalker(this)
	}

	val bridgeFinder: BridgeFinder<V>
		get() = BridgeFinderImpl(this)
	val cycleFinder: CycleFinder<V>
		get() = CycleFinderImpl(this)
	val connectivityComponentsFinder: ConnectivityComponentsFinder<V>
		get() = ConnectivityComponentsFinderImpl(this)
	val topologicalSorter: TopologicalSorter<@UnsafeVariance V>
		get() = BaseTopologicalSorter(this)
	val minPathFinder: PathFinder<V>
		get() = MinPathFinder(walker())
	val constMinPathFinder: PathFinder<V>
		get() = MiddleMinPathFinder(this)

	/** @return is v the vertex of this graph
	 */
	override operator fun contains(v: @UnsafeVariance V): Boolean {
		return IteratorUtil.contains(this, v)
	}

	val isEmpty: Boolean
		get() = size == 0

	/** @return count of vertex in this graph
	 */
	val size: Int
		get() = IteratorUtil.size(this)

	override fun inverse(): Graph<V> = InverseFiniteGraph(this)
	fun toInverse(): Graph<V> = DirectedGraph(this)
}