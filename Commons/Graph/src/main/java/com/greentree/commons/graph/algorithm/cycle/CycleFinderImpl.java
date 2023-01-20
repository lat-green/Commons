package com.greentree.commons.graph.algorithm.cycle;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import com.greentree.commons.graph.Graph;
import com.greentree.commons.graph.algorithm.path.FullGraph;
import com.greentree.commons.graph.algorithm.path.HasPath;
import com.greentree.commons.util.cortege.Pair;

public class CycleFinderImpl<V> implements CycleFinder<V> {
	
	private final Graph<V> graph;
	private final HasPath<V> hasPath;
	private final Stack<V> path = new Stack<>();
	private final Collection<VertexCycle<V>> res = new HashSet<>();
	
	private V start;
	private final Set<Pair<V, V>> used;
	
	private final Set<V> usedVertex;
	
	public CycleFinderImpl(Graph<V> graph) {
		this.graph = graph;
		this.used = new HashSet<>();
		this.usedVertex = new HashSet<>();
		this.hasPath = new FullGraph<>(graph);
		
		for(V v : graph) {
			start = v;
			dfsFirst(v);
			usedVertex.add(v);
		}
	}
	
	public static <V> Collection<VertexCycle<V>> get(Graph<V> abstractGraph) {
		return new CycleFinderImpl<>(abstractGraph).res;
	}
	
	public Collection<VertexCycle<V>> get() {
		return res;
	}
	
	private void dfs(V v) {
		tryAdd(v);
		dfsFirst(v);
	}
	
	private void dfsFirst(V v) {
		path.push(v);
		for(V to : graph.getJoints(v))
			if((to == start || hasPath(to, start)) && !usedVertex.contains(to)) {
				var p = new Pair<>(v, to);
				//        		System.out.println(used + " " + p);
				if(!used.contains(p)) {
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
		if(v == start) {
			VertexCycle<V> vc = new VertexCycle<>(path);
			res.add(vc);
		}
	}
	
	@Override
	public Iterable<? extends VertexCycle<V>> getCycles() {
		return res;
	}
}
