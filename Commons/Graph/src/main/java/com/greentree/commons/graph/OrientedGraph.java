package com.greentree.commons.graph;

public final class OrientedGraph<V> extends DirectedGraph<V> {
	
	private static final long serialVersionUID = 1L;
	
	public OrientedGraph() {
	}
	
	public OrientedGraph(Graph<? extends V> graph) {
		super(graph);
	}
	
	@Override
	public boolean add(V from, V to) {
		if(has(to, from))
			throw new IllegalArgumentException("has {to, from}");
		return super.add(from, to);
	}
	
	@Override
	public String toString() {
		return "OrientedGraph [" + all_arcs + "]";
	}
	
	
	
}
