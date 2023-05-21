package com.greentree.commons.graph.algorithm.sort;

import java.util.List;

import com.greentree.commons.graph.Graph;
import com.greentree.commons.graph.algorithm.walk.GraphWalker;

public record BaseTopologicalSorter<V>(GraphWalker<? extends V> walker) implements TopologicalSorter<V> {
	
	public BaseTopologicalSorter(Graph<? extends V> graph) {
		this(graph.walker());
	}
	
	@Override
	public void sort(List<? super V> list) {
		walker.visit(v -> {
			list.add(v);
			return false;
		});
	}
}
