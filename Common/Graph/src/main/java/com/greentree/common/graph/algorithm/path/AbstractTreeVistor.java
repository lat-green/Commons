package com.greentree.common.graph.algorithm.path;

import com.greentree.common.graph.MutableRootTree;
import com.greentree.common.graph.RootTree;
import com.greentree.common.graph.RootTreeBase;
import com.greentree.common.graph.algorithm.walk.GraphVisitor;

public abstract class AbstractTreeVistor<V> implements GraphVisitor<V> {
	
	private MutableRootTree<V> tree;
	private boolean close;
	
	protected AbstractTreeVistor() {
	}
	
	@Override
	public boolean startVisit(V v) {
		if(close)
			return false;
		
		tree = new RootTreeBase<>(v);
		
		return true;
	}
	
	@Override
	public boolean startVisit(V parent, V vertex) {
		if(close || tree.has(vertex))
			return false;
		
		tree.add(vertex, parent);
		
		return true;
	}
	
	@Override
	public void endVisit(V parent, V v) {
	}
	
	@Override
	public void endVisit(V v) {
		if(!tree.isEmpty())
			if(!add(tree))
				close();
		tree = null;
	}
	
	private void close() {
		close = true;
	}
	
	protected abstract boolean add(RootTree<V> tree);
	
}
