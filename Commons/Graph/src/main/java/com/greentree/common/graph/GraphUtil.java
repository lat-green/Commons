package com.greentree.common.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public final class GraphUtil {
	
	private GraphUtil() {
	}
	
	public static <V> Graph<V> inverse(Graph<? extends V> graph) {
		final var result = new DirectedGraph<V>();
		
		for(var v : graph)
			result.add(v);
		
		for(var v : graph)
			for(var to : graph.getJoints(v))
				result.add(to, v);
			
		return result;
	}
	
	public static <V> List<V> getTopologicalSort(Graph<? extends V> graph) {
		return getTopologicalSort(graph, new ArrayList<>(graph.size()));
	}
	
	public static <V, L extends List<? super V>> L getTopologicalSort(Graph<? extends V> graph, L result) {
		final var buffer = new LinkedList<V>();
		for(var v : graph)
			buffer.add(v);
		while(!buffer.isEmpty())
			getTopologicalSort_DFS(graph, buffer.peek(), buffer, result);
		return result;
	}
	
	private static <V> void getTopologicalSort_DFS(Graph<? extends V> graph, V e, LinkedList<? extends V> buffer,
			List<? super V> dest) {
		buffer.remove(e);
		for(var to : graph.getJoints(e)) {
			final var index = buffer.indexOf(to);
			if(index != -1)
				getTopologicalSort_DFS(graph, to, buffer, dest);
		}
		dest.add(e);
	}
	
	@Deprecated
	static boolean contains(Iterable<?> iterable, Object v) {
		if(iterable instanceof Collection)
			return ((Collection<?>) iterable).contains(v);
		for(var e : iterable)
			if(e.equals(v))
				return true;
		return false;
	}
	
}
