package com.greentree.commons.graph.algorithm.walk;

import com.greentree.commons.graph.Graph;

import java.util.HashSet;
import java.util.Set;

public record OneUse<V>(GraphWalker<V> origin) implements GraphWalker<V> {

    public OneUse(Graph<V> graph) {
        this(graph.walker());
    }

    private record OneUseVisitor<V>(GraphVisitor<? super V> visitor, Set<V> used) implements GraphVisitor<V> {

        @Override
        public void endVisit(V v) {
            visitor.endVisit(v);
        }

        @Override
        public void endVisit(V parent, V v) {
            visitor.endVisit(parent, v);
        }

        @Override
        public boolean startVisit(V v) {
            if (used.contains(v))
                return false;
            used.add(v);
            return visitor.startVisit(v);
        }

        @Override
        public boolean startVisit(V parent, V v) {
            if (used.contains(v))
                return false;
            used.add(v);
            return visitor.startVisit(parent, v);
        }

    }

    @Override
    public Graph<? extends V> graph() {
        return origin.graph();
    }

    @Override
    public void visit(GraphVisitor<? super V> visitor) {
        var used = new HashSet<V>();
        for (var v : graph())
            origin.visit(v, new OneUseVisitor<>(visitor, used));
    }

    @Override
    public void visit(V start, GraphVisitor<? super V> visitor) {
        var used = new HashSet<V>();
        origin.visit(start, new OneUseVisitor<>(visitor, used));
    }

}

