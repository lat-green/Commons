package com.greentree.commons.graph.algorithm.path;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

import com.greentree.commons.graph.Graph;

public class DijkstraMinPathFinder<V> {
	
	private final BiFunction<? super V, ? super V, ? extends Number> arc_len;
	private final Graph<? extends V> graph;
	private final HashMap<V, V> p = new HashMap<>();
	private final V start;
	
	public DijkstraMinPathFinder(Graph<? extends V> graph, V start) {
		this(graph, start, (a, b)->1);
	}
	
	public DijkstraMinPathFinder(Graph<? extends V> graph, V start,
			BiFunction<? super V, ? super V, ? extends Number> arc_len) {
		this.graph = graph;
		this.arc_len = arc_len;
		this.start = start;
		
		final var d = new HashMap<V, Number>();
		final var q = new ArrayList<V>();
		
		final Comparator<V> cmp = Comparator.comparingDouble(n->d.get(n).doubleValue());
		
		fill(d, graph, Double.MAX_VALUE);
		d.put(start, 0f);
		q.add(start);
		while(!q.isEmpty()) {
			final var v = get(q);
			q.remove(v);
			final var v_len = d.get(v).doubleValue();
			
			for(var to : graph.getAdjacencyIterable(v)) {
				final var len = d.get(to).doubleValue();
				final var new_len = v_len + arc_len.apply(v, to).doubleValue();
				if(new_len < len) {
					q.remove(to);
					d.put(to, new_len);
					p.put(to, v);
					final var index = Collections.binarySearch(q, to, cmp);
					if(index >= 0)
						q.add(index, to);
					else
						q.add(-index - 1, to);
				}
			}
		}
	}
	
	private static <T> void fill(Map<? super T, ? super Double> map, Iterable<? extends T> vertex,
			double value) {
		final var d = value;
		for(var v : vertex)
			map.put(v, d);
	}
	
	public VertexPath<V> get(V finish) {
		final var path = new ArrayList<V>();
		while(finish != start) {
			path.add(finish);
			finish = p.get(finish);
		}
		Collections.reverse(path);
		return new VertexPath<>(start, path);
	}
	
	public BiFunction<? super V, ? super V, ? extends Number> getArc_len() {
		return arc_len;
	}
	
	public Graph<? extends V> getGraph() {
		return graph;
	}
	
	public V getStart() {
		return start;
	}
	
	@Override
	public String toString() {
		return "DijkstraMinPathFinder [" + graph + "]";
	}
	
	private <T> T get(Iterable<? extends T> iterable) {
		for(var v : iterable)
			return v;
		return null;
	}
	
}
