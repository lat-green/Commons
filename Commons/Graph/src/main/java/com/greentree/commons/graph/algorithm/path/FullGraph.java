package com.greentree.commons.graph.algorithm.path;

import java.util.function.Predicate;

import com.greentree.commons.graph.Graph;
import com.greentree.commons.util.collection.FunctionAutoGenerateMap;


public class FullGraph<V> implements HasPath<V> {
	
	private final FunctionAutoGenerateMap<V, FunctionAutoGenerateMap<V, Boolean>> path = new FunctionAutoGenerateMap<>(
			a->new FunctionAutoGenerateMap<>(b->false));
	
	public FullGraph(Graph<V> graph) {
		for(var a : graph)
			for(var b : graph.getAdjacencyIterable(a))
				path.get(b).put(a, true);
			
		for(var b : graph) {
			var b_map = path.get(b);
			for(var a : graph)
				if(!b_map.get(a))
					for(var k : graph.getAdjacencyIterable(a)) {
						var k_map = path.get(k);
						if(b_map.get(k) && k_map.get(a)) {
							b_map.put(a, true);
							break;
						}
					}
		}
	}
	
	@Override
	public boolean hasPath(Object begin, Object end) {
		return path.get(begin).get(end);
	}
	
	@Override
	public Predicate<? super Object> hasPathFrom(Object v) {
		final var map = path.get(v);
		return to->map.containsKey(to);
	}
	
	@Override
	public String toString() {
		return "FullGraph [path=" + path + "]";
	}
	
}
