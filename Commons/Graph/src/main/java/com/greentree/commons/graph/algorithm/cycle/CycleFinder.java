package com.greentree.commons.graph.algorithm.cycle;

public interface CycleFinder<V> {
	
	Iterable<? extends VertexCycle<V>> getCycles();
	
}
