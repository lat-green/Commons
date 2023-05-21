package com.greentree.commons.graph.algorithm.sort;

import java.util.List;

public interface TopologicalSorter<V> {
	
	void sort(List<? super V> list);
	
}
