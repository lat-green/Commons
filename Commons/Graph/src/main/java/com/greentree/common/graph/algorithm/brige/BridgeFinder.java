package com.greentree.common.graph.algorithm.brige;

import com.greentree.common.graph.DirectedArc;

public interface BridgeFinder<V> {
	
	Iterable<? extends DirectedArc<V>> getBridges();
	
}
