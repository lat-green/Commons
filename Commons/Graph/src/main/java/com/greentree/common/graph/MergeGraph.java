package com.greentree.common.graph;

import java.util.Iterator;

import com.greentree.common.util.iterator.IteratorUtil;

public final class MergeGraph<V> implements Graph<V> {
	
	private static final long serialVersionUID = 1L;
	private final Iterable<? extends Graph<? extends V>> base;
	
	public MergeGraph(Iterable<? extends Graph<? extends V>> base) {
		this.base = base;
	}
	
	@SafeVarargs
	public MergeGraph(Graph<? extends V>... base) {
		this(IteratorUtil.iterable(base));
	}
	
	@Override
	public Iterable<? extends V> getJoints(Object v) {
		return IteratorUtil.union(IteratorUtil.map(base, g->g.getJoints(v)));
	}
	
	@Override
	public Iterator<V> iterator() {
		return IteratorUtil.union(base).iterator();
	}
	
	@Override
	public boolean has(Object from, Object to) {
		return IteratorUtil.any(base, g->g.has(from, to));
	}
	
	@Override
	public boolean has(Object v) {
		return IteratorUtil.any(base, g->g.has(v));
	}
	
}
