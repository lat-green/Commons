package com.greentree.commons.graph.algorithm.cycle;

import java.util.Collection;

public interface CycleFinder<V> {
	
	Collection<? extends VertexCycle<V>> getCycles();
	
}
