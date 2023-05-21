package com.greentree.commons.graph;

import java.util.ArrayList;

import com.greentree.commons.graph.algorithm.lca.BruteLCAFinder;
import com.greentree.commons.util.iterator.IteratorUtil;


public interface RootTree<V> extends Tree<V> {
	
	default boolean containsInSubTree(Object v, Object parent) {
		for(var e : getChildrenClosure(parent))
			if(v.equals(e))
				return true;
		return false;
	}
	
	default V lca(Object a, Object b) {
		final var finder = new BruteLCAFinder<>(this);
		return finder.lca(a, b);
	}
	
	default V getRoot() {
		final var iter = iterator();
		V p, v;
		p = iter.next();
		do {
			v = p;
			p = getParent(v);
		}while(v != p);
		return v;
	}
	
	default int depth(Object v) {
		int depth = 0;
		V p = null;
		while(true) {
			p = getParent(v);
			if(p == v)
				return depth;
			depth++;
			v = p;
		}
	}
	
	/** @return parent of vertex, or root if vertex is root */
	V getParent(Object v);
	
	default Iterable<? extends V> getChildren(Object v) {
		final var result = new ArrayList<V>();
		for(var e : this) {
			final var p = getParent(e);
			if(p.equals(v))
				result.add(e);
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	default Iterable<? extends V> getChildrenClosure(Object v) {
		final var result = new ArrayList<V>();
		result.add((V) v);
		for(var c : getChildren(v)) {
			for(var e : getChildrenClosure(c))
				result.add(e);
		}
		return result;
	}
	
	@Override
	default Iterable<? extends V> getAdjacencyIterable(Object v) {
		return IteratorUtil.union(getChildren(v), IteratorUtil.iterable(getParent(v)));
	}
	
	
}
