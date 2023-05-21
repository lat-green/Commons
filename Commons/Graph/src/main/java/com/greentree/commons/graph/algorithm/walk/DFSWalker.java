package com.greentree.commons.graph.algorithm.walk;

import java.util.Collection;
import java.util.HashSet;

import com.greentree.commons.graph.Graph;

public final class DFSWalker<V> implements GraphWalker<V> {
	
	private final Graph<? extends V> graph;
	
	public DFSWalker(Graph<? extends V> graph) {
		this.graph = graph;
	}
	
	@Override
	public void visit(V start, GraphVisitor<? super V> visitor) {
		final var used = new HashSet<V>();
		dfs(used, visitor, start);
	}
	
	private void dfs(Collection<? super V> used, GraphVisitor<? super V> visitor, V v) {
		if(visitor.startVisit(v))
			visit(used, visitor, v);
		visitor.endVisit(v);
	}
	
	private void dfs(Collection<? super V> used, GraphVisitor<? super V> visitor, V p, V v) {
		if(visitor.startVisit(p, v))
			visit(used, visitor, v);
		visitor.endVisit(p, v);
	}
	
	
	private void visit(Collection<? super V> used, GraphVisitor<? super V> visitor, V v) {
		used.add(v);
		for(var to : graph.getAdjacencyIterable(v))
			if(!used.contains(to))
				dfs(used, visitor, v, to);
		used.remove(v);
	}
	
	@Override
	public Graph<? extends V> graph() {
		return graph;
	}
	
}
