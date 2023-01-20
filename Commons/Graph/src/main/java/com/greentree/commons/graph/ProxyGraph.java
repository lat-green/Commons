package com.greentree.commons.graph;

import java.util.Iterator;

import com.greentree.commons.graph.algorithm.walk.GraphWalker;

public class ProxyGraph<V> implements Graph<V> {
	
	private static final long serialVersionUID = 1L;
	private final Graph<V> base;
	
	protected ProxyGraph(Graph<V> base) {
		this.base = base;
	}
	
	@Override
	public Iterable<? extends V> getJoints(Object v) {
		return base.getJoints(v);
	}
	
	@Override
	public Iterator<V> iterator() {
		return base.iterator();
	}
	
	@Override
	public boolean has(Object from, Object to) {
		return base.has(from, to);
	}
	
	@Override
	public boolean has(Object v) {
		return base.has(v);
	}
	
	@Override
	public GraphWalker<V> walker() {
		return base.walker();
	}
	
}
