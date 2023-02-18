package com.greentree.commons.graph.algorithm.lca;

import com.greentree.commons.graph.RootTree;


public class BruteLCAFinder<V> implements LCAFinder<V> {
	
	private final RootTree<? extends V> tree;
	
	public BruteLCAFinder(RootTree<? extends V> tree) {
		this.tree = tree;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public V lca(Object a, Object b) {
		if(tree.containsInSubTree(a, b))
			return (V) b;
		return lca(a, tree.getParent(b));
	}
	
	
}
