package com.greentree.common.graph.algorithm.path;

import java.util.Collection;
import java.util.List;

import com.greentree.common.graph.Graph;
import com.greentree.common.util.collection.FunctionAutoGenerateMap;

public class SmartAllPathFinder<V> {
	
	private final Graph<V> graph;
	
	private final FunctionAutoGenerateMap<V, FunctionAutoGenerateMap<V, Boolean>> path = new FunctionAutoGenerateMap<>(
			a->new FunctionAutoGenerateMap<>(b->false));
	
	public SmartAllPathFinder(Graph<V> graph) {
		this.graph = graph;
		for(var a : graph)
			for(var b : graph.getJoints(a))
				path.get(b).put(a, true);
			
		for(var b : graph)
			for(var a : graph)
				for(var k : graph)
					if(path.get(b).get(k) && path.get(k).get(a))
						path.get(b).put(a, true);
	}
	
	public Collection<List<? extends V>> get(V from, V to) {
		//		return AllPathFinder.get(graph, from, to, path.get(to));
		return AllPathFinder.get(graph, from, to);
	}
	
}
