package com.greentree.commons.graph;

public interface MutableRootTree<V> extends RootTree<V> {

    void remove(V vertex);

    void clear();

    default void addAll(RootTree<? extends V> tree) {
        for (var arc : tree.getJoints())
            add(arc.getBegin(), arc.getEnd());
    }

    void add(V vertex, V parent);

}
