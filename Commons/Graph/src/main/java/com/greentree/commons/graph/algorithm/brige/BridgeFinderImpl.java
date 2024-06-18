package com.greentree.commons.graph.algorithm.brige;

import com.greentree.commons.graph.DirectedArc;
import com.greentree.commons.graph.FiniteGraph;
import com.greentree.commons.util.collection.FunctionAutoGenerateMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class BridgeFinderImpl<V> implements BridgeFinder<V> {

    private final FiniteGraph<V> graph;
    private final Map<V, Integer> tin = new FunctionAutoGenerateMap<>(v -> 0),
            fup = new FunctionAutoGenerateMap<>(v -> 0);
    private final Map<V, Boolean> used = new FunctionAutoGenerateMap<>(v -> false);
    private final Map<V, Collection<V>> res = new FunctionAutoGenerateMap<>(() -> new ArrayList<>());
    private int timer;

    public BridgeFinderImpl(FiniteGraph<V> graph) {
        this.graph = graph;
        for (var v : graph)
            if (!used.get(v))
                dfs(v);
    }

    @Override
    public Iterable<DirectedArc<V>> getBridges() {
        final var result = new ArrayList<DirectedArc<V>>();
        for (var b : res.keySet())
            for (var e : res.get(b))
                result.add(new DirectedArc<>(b, e));
        return result;
    }

    private void dfs(V v, V p) {
        used.put(v, true);
        tin.put(v, timer);
        fup.put(v, timer);
        timer++;
        for (var to : graph.getAdjacencyIterable(v)) {
            if (to == p)
                continue;
            if (used.get(to))
                fup.put(v, Math.min(fup.get(v), tin.get(to)));
            else {
                dfs(to, v);
                fup.put(v, Math.min(fup.get(v), fup.get(to)));
                if (fup.get(to) > tin.get(v))
                    res.get(v).add(to);
            }
        }
    }

    private void dfs(V v) {
        dfs(v, null);
    }

}
