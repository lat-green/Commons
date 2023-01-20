package com.greentree.common.graph.algorithm.walk;


public class ProxyGraphVisitor<V> implements GraphVisitor<V> {
	
	private final GraphVisitor<? super V> base;
	
	protected ProxyGraphVisitor(GraphVisitor<? super V> base) {
		this.base = base;
	}
	
	@Override
	public void endVisit(V v) {
		base.endVisit(v);
	}
	
	@Override
	public void endVisit(V parent, V v) {
		base.endVisit(parent, v);
	}
	
	@Override
	public boolean startVisit(V v) {
		return base.startVisit(v);
	}
	
	@Override
	public boolean startVisit(V parent, V v) {
		return base.startVisit(parent, v);
	}
	
	
	
}
