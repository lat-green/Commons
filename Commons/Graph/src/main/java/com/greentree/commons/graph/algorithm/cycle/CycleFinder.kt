package com.greentree.commons.graph.algorithm.cycle

interface CycleFinder<out V : Any> {

	val cycles: Collection<VertexCycle<V>>
}
