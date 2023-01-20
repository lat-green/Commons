package com.greentree.commons.graph.algorithm.path;

import java.util.List;

import com.greentree.commons.graph.Graph;
import com.greentree.commons.graph.algorithm.walk.GraphWalker;

public final class SomePathFinder<V> implements PathFinder<V> {
	
	private final GraphWalker<V> walker;
	
	public SomePathFinder(Graph<V> graph) {
		this(graph.walker());
	}
	
	public SomePathFinder(GraphWalker<V> walker) {
		this.walker = walker;
	}
	
	public static <V> VertexPath<V> get(Graph<V> graph, V begin, V end) {
		return new SomePathFinder<>(graph).getPath(begin, end);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public VertexPath<V> getPath(V begin, V end) {
		final List<? extends V>[] result = new List[1];
		final var visitor = new AbstractPathVistor<>(end) {
			
			@Override
			protected boolean add(List<V> path) {
				result[0] = path;
				return false;
			}
			
		};
		walker.visit(begin, visitor);
		return new VertexPath<>(result[0]);
	}
	
	@Override
	public String toString() {
		return "AllPathFinder [" + walker + "]";
	}
	
}
