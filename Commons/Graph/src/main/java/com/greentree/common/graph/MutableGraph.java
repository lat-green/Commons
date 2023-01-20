package com.greentree.common.graph;

public interface MutableGraph<V> extends Graph<V> {
	
	void add(V v);
	
	boolean add(V from, V to);
	
	void clear();
	
	boolean remove(V v);
	
	boolean remove(V from, V to);
	
	default void addAll(Graph<? extends V> graph) {
		for(var v : graph) {
			add(v);
			for(var to : graph.getJoints(v)) {
				add(v, to);
			}
		}
	}
	
}
