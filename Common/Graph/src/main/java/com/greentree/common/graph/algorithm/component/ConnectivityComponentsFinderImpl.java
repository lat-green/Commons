package com.greentree.common.graph.algorithm.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import com.greentree.common.graph.Graph;
import com.greentree.common.graph.algorithm.walk.DFSWalker;
import com.greentree.common.graph.algorithm.walk.GraphVisitor;

public class ConnectivityComponentsFinderImpl<V> implements ConnectivityComponentsFinder<V> {
	
	private final Graph<? extends V> graph;
	
	public ConnectivityComponentsFinderImpl(Graph<? extends V> graph) {
		this.graph = graph;
	}
	
	public static <V> Collection<? extends Collection<V>> getConnectivityComponents(
			Graph<V> graph) {
		return new ConnectivityComponentsFinderImpl<>(graph).getComponents();
	}
	
	public List<? extends Collection<V>> getComponents() {
		final var walker = new DFSWalker<V>(graph);
		final var all = new HashSet<V>();
		final var c = new HashSet<V>();
		final var cc = new ArrayList<ArrayList<V>>();
		final var visitor = (GraphVisitor<V>) v-> {
			c.add(v);
			all.add(v);
			return true;
		};
		
		for(var v : graph)
			if(!all.contains(v)) {
				walker.visit(v, visitor);
				cc.add(new ArrayList<>(c));
				c.clear();
			}
		
		return cc;
	}
	
}
