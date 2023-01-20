package com.greentree.common.graph.algorithm.cycle;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

import com.greentree.common.graph.DirectedGraph;
import com.greentree.common.graph.Graph;
import com.greentree.common.graph.algorithm.brige.BridgeFinderImpl;
import com.greentree.common.graph.algorithm.path.FullGraph;
import com.greentree.common.graph.algorithm.path.HasPath;

public class CycleFinder2<V extends Comparable<? super V>> {
	
	private final DirectedGraph<V> graph;
	private final HasPath<V> hasPath;
	private final Stack<V> path = new Stack<>();
	
	private final Collection<VertexCycle<V>> res = new HashSet<>();
	private V start;
	
	private final Set<V> used = new HashSet<>();
	
	public CycleFinder2(Graph<V> g) {
		this.graph = new DirectedGraph<>(g);
		
		//		System.out.println(graph);
		removeBrige(graph);
		//		System.out.println(graph);
		
		this.hasPath = new FullGraph<>(graph);
		
		for(V v : graph) {
			start = v;
			dfs(v);
			used.add(v);
		}
	}
	
	public static <T extends Comparable<? super T>> boolean isSorted(Iterable<T> iterable) {
		Iterator<T> iter = iterable.iterator();
		if(!iter.hasNext())
			return true;
		T t = iter.next();
		while(iter.hasNext()) {
			T t2 = iter.next();
			if(t.compareTo(t2) > 0)
				return false;
			t = t2;
		}
		return true;
	}
	
	private static <V> boolean removeBrige(DirectedGraph<V> graph) {
		var briges = BridgeFinderImpl.getBridges(graph);
		
		boolean res = false;
		
		for(var arc : briges)
			res |= graph.remove(arc.begin(), arc.end());
		
		return res;
	}
	
	public Collection<VertexCycle<V>> get() {
		return res;
	}
	
	private void dfs(V v) {
		dfs0(v, null);
	}
	
	private void dfs(V v, V p, List<V> next) {
		path.push(v);
		used.add(v);
		
		//		System.out.println("-".repeat(path.size()-1) + v + " " + next);
		
		next.forEach(to-> {
			if(to != start)
				dfs0(to, v);
			else
				tryAdd();
		});
		
		used.remove(v);
		path.pop();
	}
	
	private void dfs0(V to, V v) {
		List<V> n = get(to, v);
		if(!n.isEmpty())
			dfs(to, v, n);
	}
	
	private List<V> get(V v, V p) {
		return graph.getJoints(v).stream()
				.filter(to->(to == start && to != p || hasPath(to, start) && !used.contains(to)))
				.collect(Collectors.toList());
	}
	
	private boolean hasPath(V a, V b) {
		return hasPath.hasPath(a, b);
	}
	
	private void tryAdd() {
		//if(isSorted(path))
		{
			//			System.out.println("add");
			res.add(new VertexCycle<>(path));
		}
	}
	
}
