package com.greentree.commons.graph.algorithm.path;

import com.greentree.commons.graph.MutableRootTree;
import com.greentree.commons.graph.RootTree;
import com.greentree.commons.graph.RootTreeBase;
import com.greentree.commons.graph.algorithm.walk.vertex.VertexVisitor;

public abstract class AbstractTreeVistor<V> implements VertexVisitor<V> {

    private MutableRootTree<V> tree;
    private boolean close;

    protected AbstractTreeVistor() {
    }

    @Override
    public void endVisit(V v) {
        if (!tree.isEmpty())
            if (!add(tree))
                close();
        tree = null;
    }

    protected abstract boolean add(RootTree<V> tree);

    private void close() {
        close = true;
    }

    @Override
    public void endVisit(V parent, V v) {
    }

    @Override
    public boolean startVisit(V v) {
        if (close)
            return false;
        tree = new RootTreeBase<>(v);
        return true;
    }

    @Override
    public boolean startVisit(V parent, V vertex) {
        if (close || tree.contains(vertex))
            return false;
        tree.add(vertex, parent);
        return true;
    }

}
