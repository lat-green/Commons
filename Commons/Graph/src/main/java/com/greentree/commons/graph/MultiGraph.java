package com.greentree.commons.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class MultiGraph<V> implements MutableGraph<V> {
	
	private static final long serialVersionUID = 1L;
	
	protected final Map<V, List<V>> all_arcs = new HashMap<>();
	protected final Map<V, Set<V>> arcs = new HashMap<>();
	
	public MultiGraph() {
	}
	
	public MultiGraph(Graph<? extends V> graph) {
		addAll(graph);
	}
	
	@Override
	public void add(V v) {
		if(has(v))
			return;
		all_arcs.put(v, new ArrayList<>());
		arcs.put(v, new HashSet<>());
	}
	
	@Override
	public boolean add(V from, V to) {
		add(from);
		add(to);
		arcs.get(from).add(to);
		all_arcs.get(from).add(to);
		return true;
	}
	
	@Override
	public void clear() {
		all_arcs.clear();
		arcs.clear();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(!(obj instanceof MultiGraph))
			return false;
		MultiGraph<?> other = (MultiGraph<?>) obj;
		return Objects.equals(all_arcs, other.all_arcs);
	}
	
	@Override
	public Collection<? extends V> getAdjacencyIterable(Object v) {
		if(all_arcs.containsKey(v))
			return all_arcs.get(v);
		throw new IllegalArgumentException("not vertex v:" + v);
	}
	
	@Override
	public boolean has(Object v) {
		return all_arcs.containsKey(v);
	}
	
	@Override
	public boolean has(Object from, Object to) {
		if(!has(from))
			return false;
		return arcs.get(from).contains(to);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(all_arcs);
	}
	
	@Override
	public Iterator<V> iterator() {
		return all_arcs.keySet().iterator();
	}
	
	@Override
	public boolean remove(V v) {
		if(!has(v))
			return false;
		all_arcs.get(v).clear();
		all_arcs.remove(v);
		arcs.get(v).clear();
		arcs.remove(v);
		for(var b : all_arcs.values())
			b.remove(v);
		for(var b : arcs.values())
			b.remove(v);
		return true;
	}
	
	@Override
	public boolean remove(V from, V to) {
		if(!has(from))
			return false;
		final var tos = all_arcs.get(from);
		if(tos.remove(to)) {
			if(!tos.contains(to))
				arcs.get(from).remove(to);
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "MultiGraph [" + all_arcs + "]";
	}
	
}
