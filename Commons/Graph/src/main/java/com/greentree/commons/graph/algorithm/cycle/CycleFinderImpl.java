package com.greentree.commons.graph.algorithm.cycle;

import com.greentree.commons.graph.FiniteGraph;
import com.greentree.commons.graph.algorithm.path.FullGraph;
import com.greentree.commons.graph.algorithm.path.HasPath;
import com.greentree.commons.util.cortege.Pair;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class CycleFinderImpl<V> implements CycleFinder<V> {

    private final FiniteGraph<V> graph;
    private final HasPath<V> hasPath;
    private final Stack<V> path = new Stack<>();
    private final Collection<VertexCycle<V>> res = new HashSet<>();
    private final Set<Pair<V, V>> used;
    private final Set<V> usedVertex;
    private V start;

    public CycleFinderImpl(FiniteGraph<V> graph) {
        this.graph = graph;
        this.used = new HashSet<>();
        this.usedVertex = new HashSet<>();
        this.hasPath = new FullGraph<>(graph);
        for (V v : graph) {
            start = v;
            dfsFirst(v);
            usedVertex.add(v);
        }
    }

    public Collection<VertexCycle<V>> get() {
        return res;
    }

    @Override
    public Collection<VertexCycle<V>> getCycles() {
        return res;
    }

    private void dfs(V v) {
        tryAdd(v);
        dfsFirst(v);
    }

    private void dfsFirst(V v) {
        path.push(v);
        for (V to : graph.getAdjacencyIterable(v))
            if ((to == start || hasPath(to, start)) && !usedVertex.contains(to)) {
                var p = new Pair<>(v, to);
                //        		System.out.println(used + " " + p);
                if (!used.contains(p)) {
                    used.add(p);
                    dfs(to);
                    used.remove(p);
                }
            }
        path.pop();
    }

    private boolean hasPath(V a, V b) {
        return hasPath.hasPath(a, b);
    }

    private void tryAdd(V v) {
        if (v == start) {
            VertexCycle<V> vc = new VertexCycle<>(path);
            res.add(vc);
        }
    }

}
