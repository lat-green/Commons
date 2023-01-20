package com.greentree.commons.graph.algorithm.walk;

import java.util.HashSet;
import java.util.Set;

import com.greentree.commons.graph.Graph;
import com.greentree.commons.graph.algorithm.component.ConnectivityComponentsFinderImpl;

public final class OneUseDFSWalker<V> implements GraphWalker<V> {
	
	private final Graph<? extends V> graph;
	
	public OneUseDFSWalker(Graph<? extends V> graph) {
		this.graph = graph;
	}
	
	@Override
	public Graph<? extends V> graph() {
		return graph;
	}
	
	@Override
	public void visit(V start, GraphVisitor<? super V> visitor) {
		final var used = new HashSet<V>();
		final var used_all = new HashSet<V>();
		final var cc = new ConnectivityComponentsFinderImpl<V>(graph).getComponents();
		for(var c : cc) {
			if(c.contains(start))
				continue;
			used_all.addAll(c);
		}
		dfs(used, used_all, visitor, start);
	}
	
	private void dfs(Set<? super V> used, Set<? super V> used_all, GraphVisitor<? super V> visitor,
			V v) {
		if(visitor.startVisit(v)) {
			visit(used, used_all, visitor, v);
		}
		visitor.endVisit(v);
	}
	
	private void dfs(Set<? super V> used, Set<? super V> used_all, GraphVisitor<? super V> visitor,
			V p, V v) {
		if(visitor.startVisit(p, v)) {
			visit(used, used_all, visitor, v);
		}
		visitor.endVisit(p, v);
	}
	
	
	private void visit(Set<? super V> used, Set<? super V> used_all,
			GraphVisitor<? super V> visitor, V v) {
		used_all.add(v);
		if(used_all.size() >= graph.size())
			return;
		used.add(v);
		for(var to : graph.getJoints(v))
			if(!used.contains(to))
				dfs(used, used_all, visitor, v, to);
		used.remove(v);
	}
	
}
