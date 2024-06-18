package com.greentree.commons.graph.algorithm.walk;

import com.greentree.commons.graph.Tree;

public interface TreeWalker<V> extends GraphWalker<V> {

    Tree<? extends V> graph();

}
