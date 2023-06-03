package com.greentree.commons.graph.algorithm.sort;

import com.greentree.commons.graph.Graph;
import com.greentree.commons.graph.algorithm.walk.GraphVisitor;
import com.greentree.commons.graph.algorithm.walk.OneUse;

import java.util.List;

public record BaseTopologicalSorter<V>(Graph<? extends V> graph) implements TopologicalSorter<V> {

    @Override
    public void sort(List<? super V> list) {
        var graph = this.graph.inverse();
        var walker = new OneUse<>(graph);
        walker.visit(new GraphVisitor<V>() {
            @Override
            public void endVisit(V v) {
                list.add(v);
            }

            @Override
            public boolean startVisit(V v) {
                return true;
            }
        });
    }

}
