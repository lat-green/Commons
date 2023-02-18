package com.greentree.commons.graph.algorithm.walk;

import com.greentree.commons.graph.RootTree;

public class ParentWalker<V> implements RootTreeWalker<V> {
	
	private final RootTree<? extends V> tree;
	
	public ParentWalker(RootTree<? extends V> tree) {
		this.tree = tree;
	}
	
	@Override
	public void visit(V start, GraphVisitor<? super V> visitor) {
		walk(visitor, start);
	}
	
	private void walk(GraphVisitor<? super V> visitor, V v) {
		visitor.startVisit(v);
		final var to = tree.getParent(v);
		walk(visitor, to, v);
		visitor.endVisit(v);
	}
	
	private void walk(GraphVisitor<? super V> visitor, V v, V parent) {
		visitor.startVisit(parent, v);
		final var to = tree.getParent(v);
		walk(visitor, to, v);
		visitor.endVisit(parent, v);
	}
	
	@Override
	public RootTree<? extends V> graph() {
		return tree;
	}
	
}
