package com.greentree.commons.graph;

import com.greentree.commons.util.iterator.IteratorUtil;


public interface RootTree<V> extends Tree<V> {
	
	default V lca(Object a, Object b) {
		return null;
	}
	
	default V getRoot() {
		final var iter = iterator();
		if(!iter.hasNext())
			return null; // empty tree
			
		V p, v;
		p = iter.next();
		do {
			v = p;
			p = getParent(v);
		}while(v != p);
		return v;
	}
	
	/** @return parent of vertex, or root if vertex is root */
	V getParent(Object v);
	Iterable<? extends V> getChildren(Object v);
	
	@Override
	default Iterable<? extends V> getJoints(Object v) {
		return IteratorUtil.union(getChildren(v), IteratorUtil.iterable(getParent(v)));
	}
	
	
}
