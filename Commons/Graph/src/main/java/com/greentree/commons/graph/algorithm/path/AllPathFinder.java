package com.greentree.commons.graph.algorithm.path;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.greentree.commons.graph.Graph;
import com.greentree.commons.graph.algorithm.walk.GraphWalker;

public final class AllPathFinder<V> {
	
	private final GraphWalker<V> walker;
	
	public AllPathFinder(GraphWalker<V> walker) {
		this.walker = walker;
	}
	
	public AllPathFinder(Graph<V> graph) {
		this(graph.walker());
	}
	
	public Collection<List<? extends V>> get(V begin, V end) {
		final var result = new ArrayList<List<? extends V>>();
		final var visitor = new AbstractPathVistor<>(end) {
			
			@Override
			protected boolean add(List<V> path) {
				result.add(path);
				return true;
			}
			
		};
		walker.visit(begin, visitor);
		return result;
	}
	
	@Override
	public String toString() {
		return "AllPathFinder [" + walker + "]";
	}
	
}
