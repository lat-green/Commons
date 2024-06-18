package com.greentree.commons.graph.algorithm.brige

import com.greentree.commons.graph.DirectedArc

interface BridgeFinder<out V : Any> {

	val bridges: Iterable<DirectedArc<V>>
}
