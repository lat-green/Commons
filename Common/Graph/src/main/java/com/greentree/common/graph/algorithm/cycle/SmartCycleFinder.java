package com.greentree.common.graph.algorithm.cycle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.greentree.common.graph.Graph;

public class SmartCycleFinder<V> {
	
	private final List<JointCycle<V>> res = new ArrayList<>();
	
	public SmartCycleFinder(Graph<V> graph) {
		Map<V, Boolean> color = new HashMap<>();
		for(V v : graph)
			color.put(v, false);
		for(V v : graph) {
			dfs(graph, new HashMap<>(), color, v);
			break;
		}
		res.removeIf(e->(e.size() < 3));
	}
	
	public List<JointCycle<V>> get() {
		return res;
	}
	
	private void dfs(Graph<V> graph, Map<V, V> parent, Map<V, Boolean> color, V v) {
		color.put(v, true);
		for(V to : graph.getJoints(v))
			if(!color.get(to)) {
				parent.put(to, v);
				dfs(graph, parent, color, to);
			}else {
				List<V> cycle = new ArrayList<>();
				for(V t = v;; t = parent.get(t)) {
					cycle.add(t);
					if(t.equals(to))
						break;
				}
				JointCycle<V> c0 = new JointCycle<>(cycle);
				if(!res.contains(c0))
					res.add(c0);
			}
		color.put(v, false);
	}
}
