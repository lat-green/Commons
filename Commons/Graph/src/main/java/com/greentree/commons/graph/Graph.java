package com.greentree.commons.graph;

import com.greentree.commons.graph.algorithm.brige.BridgeFinder;
import com.greentree.commons.graph.algorithm.brige.BridgeFinderImpl;
import com.greentree.commons.graph.algorithm.component.ConnectivityComponentsFinder;
import com.greentree.commons.graph.algorithm.component.ConnectivityComponentsFinderImpl;
import com.greentree.commons.graph.algorithm.cycle.CycleFinder;
import com.greentree.commons.graph.algorithm.cycle.CycleFinderImpl;
import com.greentree.commons.graph.algorithm.path.MiddleMinPathFinder;
import com.greentree.commons.graph.algorithm.path.MinPathFinder;
import com.greentree.commons.graph.algorithm.path.PathFinder;
import com.greentree.commons.graph.algorithm.sort.BaseTopologicalSorter;
import com.greentree.commons.graph.algorithm.sort.TopologicalSorter;
import com.greentree.commons.graph.algorithm.walk.DFSWalker;
import com.greentree.commons.graph.algorithm.walk.GraphWalker;
import com.greentree.commons.util.iterator.IteratorUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public interface Graph<V> extends Iterable<V>, Serializable {

    default Graph<V> inverse() {
        final var result = new DirectedGraph<V>();
        for (var v : this)
            result.add(v);
        for (var v : this)
            for (var to : getAdjacencyIterable(v))
                result.add(to, v);
        return result;
    }

    Iterable<? extends V> getAdjacencyIterable(Object v);

    default boolean contains(Object v) {
        for (var e : this)
            if (v.equals(e))
                return true;
        return false;
    }

    default PathFinder<V> getConstMinPathFinder() {
        return new MiddleMinPathFinder<>(this);
    }

    default Iterable<? extends DirectedArc<V>> getJoints() {
        final var result = new ArrayList<DirectedArc<V>>();
        for (var begin : this)
            for (var end : getAdjacencyIterable(begin))
                result.add(new DirectedArc<>(begin, end));
        return result;
    }

    default PathFinder<V> getMinPathFinder() {
        return new MinPathFinder<>(walker());
    }

    default GraphWalker<V> walker() {
        return new DFSWalker<>(this);
    }

    default BridgeFinder<V> getBridgeFinder() {
        return new BridgeFinderImpl<>(this);
    }

    default CycleFinder<V> getCycleFinder() {
        return new CycleFinderImpl<>(this);
    }

    default ConnectivityComponentsFinder<V> getConnectivityComponentsFinder() {
        return new ConnectivityComponentsFinderImpl<>(this);
    }

    default List<V> getTopologicalSort() {
        return getTopologicalSort(new ArrayList<>());
    }

    default <L extends List<? super V>> L getTopologicalSort(L result) {
        getTopologicalSorter().sort(result);
        return result;
    }

    default TopologicalSorter<V> getTopologicalSorter() {
        return new BaseTopologicalSorter<>(this);
    }

    /** @return is {from, to} the arc of this graph */
    default boolean has(Object from, Object to) {
        if (!has(from))
            return false;
        return getAdjacencyCollection(from).contains(to);
    }

    /** @return is v the vertex of this graph */
    default boolean has(Object v) {
        return IteratorUtil.contains(this, v);
    }

    default Collection<? extends V> getAdjacencyCollection(Object v) {
        var result = new ArrayList<V>();
        for (var e : getAdjacencyIterable(v))
            result.add(e);
        return Collections.unmodifiableCollection(result);
    }

    default boolean isEmpty() {
        return IteratorUtil.isEmpty(this);
    }

    /** @return count of vertex in this graph */
    default int size() {
        return IteratorUtil.size(this);
    }

}
