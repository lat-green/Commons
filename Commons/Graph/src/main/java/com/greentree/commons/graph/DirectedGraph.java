package com.greentree.commons.graph;

public class DirectedGraph<V> extends MultiFiniteGraph<V> {

    private static final long serialVersionUID = 1L;

    public DirectedGraph() {
    }

    public DirectedGraph(FiniteGraph<? extends V> graph) {
        super(graph);
    }

    public boolean add(V from, V to) {
        if (from.equals(to))
            throw new IllegalArgumentException("from == to from:" + from + " to:" + to);
        if (has(from, to))
            return false;
        return super.add(from, to);
    }

    @Override
    public String toString() {
        return "DirectedGraph<V> [" + all_arcs + "]";
    }

}
