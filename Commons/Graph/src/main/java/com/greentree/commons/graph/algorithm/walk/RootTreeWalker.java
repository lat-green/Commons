package com.greentree.commons.graph.algorithm.walk;

import com.greentree.commons.graph.RootTree;

public interface RootTreeWalker<V> extends TreeWalker<V> {
	
	
	RootTree<? extends V> graph();
	
}
