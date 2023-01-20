package com.greentree.commons.graph.algorithm.path;

public interface PathFinder<V> {
	
	VertexPath<V> getPath(V begin, V end);
	
}
