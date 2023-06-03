package com.greentree.commons.graph.algorithm.sort;

import com.greentree.commons.graph.Graph;
import com.greentree.commons.graph.algorithm.walk.GraphVisitor;
import com.greentree.commons.graph.algorithm.walk.OneUse;

import java.util.Collections;
import java.util.List;

public record BaseTopologicalSorter<V>(Graph<? extends V> graph) implements TopologicalSorter<V> {

    @Override
    public void sort(List<? super V> list) {
        var walker = new OneUse<>(graph);
        walker.visit((GraphVisitor<V>) v -> {
            list.add(0, v);
            return true;
        });
        Collections.reverse(list);
    }

}
