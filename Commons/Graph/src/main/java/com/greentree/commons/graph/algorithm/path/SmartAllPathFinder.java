package com.greentree.commons.graph.algorithm.path;

import com.greentree.commons.graph.FiniteGraph;
import com.greentree.commons.util.collection.FunctionAutoGenerateMap;

import java.util.Collection;
import java.util.List;

public class SmartAllPathFinder<V> {

    private final FiniteGraph<V> graph;

    private final FunctionAutoGenerateMap<V, FunctionAutoGenerateMap<V, Boolean>> path = new FunctionAutoGenerateMap<>(
            a -> new FunctionAutoGenerateMap<>(b -> false));

    public SmartAllPathFinder(FiniteGraph<V> graph) {
        this.graph = graph;
        for (var a : graph)
            for (var b : graph.getAdjacencyIterable(a))
                path.get(b).put(a, true);
        for (var b : graph)
            for (var a : graph)
                for (var k : graph)
                    if (path.get(b).get(k) && path.get(k).get(a))
                        path.get(b).put(a, true);
    }

    public Collection<List<? extends V>> get(V from, V to) {
        return new AllPathFinder<>(graph).get(from, to);
    }

}
