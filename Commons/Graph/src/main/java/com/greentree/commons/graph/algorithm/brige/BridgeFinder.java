package com.greentree.commons.graph.algorithm.brige;

import com.greentree.commons.graph.DirectedArc;

public interface BridgeFinder<V> {
	
	Iterable<? extends DirectedArc<V>> getBridges();
	
}
