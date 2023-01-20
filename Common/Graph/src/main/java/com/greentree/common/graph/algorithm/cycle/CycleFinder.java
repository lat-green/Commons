package com.greentree.common.graph.algorithm.cycle;

public interface CycleFinder<V> {
	
	Iterable<? extends VertexCycle<V>> getCycles();
	
}
