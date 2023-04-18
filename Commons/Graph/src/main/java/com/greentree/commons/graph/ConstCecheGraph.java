package com.greentree.commons.graph;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ConstCecheGraph<V> implements Graph<V> {
	private static final long serialVersionUID = 1L;
	private final Graph<V> base;
	private final Map<Object, Iterable<? extends V>> ceche = new HashMap<>();
	public ConstCecheGraph(Graph<V> base) {
		this.base = base;
	}
	
	@Override
	public Iterable<? extends V> getJoints(Object v) {
		if(ceche.containsKey(v))
			return ceche.get(v);
		System.out.println(v);
		var result = base.getJoints(v);
		ceche.put(v, result);
		return result;
	}
	
	@Override
	public Iterator<V> iterator() {
		return base.iterator();
	}
	
}
