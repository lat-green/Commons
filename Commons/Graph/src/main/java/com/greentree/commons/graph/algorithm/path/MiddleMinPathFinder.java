package com.greentree.commons.graph.algorithm.path;

import com.greentree.commons.graph.FiniteGraph;
import com.greentree.commons.util.collection.FunctionAutoGenerateMap;
import kotlin.jvm.functions.Function2;

import java.util.HashMap;
import java.util.Map;

/**
 * <a href="https://e-maxx.ru/algo/floyd_warshall_algorithm">floyd-warshall
 * algorithm</a>
 */
public class MiddleMinPathFinder<V> implements MiddlePathFinder<V> {

    private final Map<V, Map<V, V>> middle = new FunctionAutoGenerateMap<>(v -> new HashMap<>());

    public MiddleMinPathFinder(FiniteGraph<V> graph) {
        this(graph, GraphPathBaseDistance.Companion.instance());
    }

    public MiddleMinPathFinder(FiniteGraph<V> graph,
                               Function2<? super V, ? super V, ? extends Number> arc_len) {
        final var distances = new HashMap<V, Map<V, Double>>();
        for (V a : graph) {
            final var a_distance = new HashMap<V, Double>();
            distances.put(a, a_distance);
            for (V b : graph)
                a_distance.put(b, Double.MAX_VALUE);
            for (V b : graph.getAdjacencyIterable(a))
                a_distance.put(b, arc_len.invoke(a, b).doubleValue());
        }
        for (V a : graph) {
            final var a_middle = middle.get(a);
            for (V b : graph.getAdjacencyIterable(a))
                a_middle.put(b, a);
        }
        for (V k : graph) {
            final var k_distance = distances.get(k);
            for (V a : graph) {
                final var a_distance = distances.get(a);
                for (V b : graph)
                    if (k != b) {
                        final var new_dis_ab = a_distance.get(k) + k_distance.get(b);
                        if (a_distance.get(b) > new_dis_ab) {
                            a_distance.put(b, new_dis_ab);
                            middle.get(a).put(b, k);
                        }
                    }
            }
        }
    }

    @Override
    public String toString() {
        return "MiddleMinPathFinder [" + middle + "]";
    }

    @Override
    public V getPathMidle(Object begin, Object end) {
        return middle.get(begin).get(end);
    }

}
