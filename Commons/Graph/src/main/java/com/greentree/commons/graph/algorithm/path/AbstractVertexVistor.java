package com.greentree.commons.graph.algorithm.path;

import com.greentree.commons.graph.algorithm.walk.vertex.VertexVisitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public abstract class AbstractVertexVistor<V> implements VertexVisitor<V> {

    protected final V end;
    private final Stack<V> path = new Stack<>();
    private boolean close;

    protected AbstractVertexVistor(V end) {
        this.end = end;
    }

    @Override
    public boolean startVisit(V parent, V v) {
        return startVisit(v);
    }

    @Override
    public boolean startVisit(V v) {
        path.add(v);
        return !close;
    }

    @Override
    public void endVisit(V parent, V v) {
        endVisit(v);
    }

    @Override
    public void endVisit(V v) {
        if (v.equals(end))
            if (!add(new ArrayList<>(path)))
                close();
        path.pop();
    }

    protected abstract boolean add(List<V> path);

    private void close() {
        close = true;
    }

}
