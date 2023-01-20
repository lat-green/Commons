package com.greentree.commons.graph.algorithm.brige;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.greentree.commons.graph.DirectedArc;
import com.greentree.commons.graph.Graph;
import com.greentree.commons.util.collection.AutoGenerateMap;
import com.greentree.commons.util.collection.FunctionAutoGenerateMap;
import com.greentree.commons.util.function.LambdaSaveFunction;
import com.greentree.commons.util.function.SaveFunction;

public class BridgeFinderImpl<V> implements BridgeFinder<V> {
	
	private final Graph<V> graph;
	private Map<V, Collection<V>> res = new AutoGenerateMap<>() {
		
		@Override
		protected Collection<V> generate(V k) {
			return new ArrayList<>();
		}
	};
	
	private int timer;
	
	private final Map<V, Integer> tin = new FunctionAutoGenerateMap<>(v->0),
			fup = new FunctionAutoGenerateMap<>(v->0);
	
	private final Map<V, Boolean> used = new FunctionAutoGenerateMap<>(v->false);
	
	public BridgeFinderImpl(Graph<V> graph) {
		this.graph = graph;
		for(var v : graph)
			if(!used.get(v))
				dfs(v);
	}
	
	public static <V> Iterable<? extends DirectedArc<V>> getBridges(Graph<V> graph) {
		return new BridgeFinderImpl<>(graph).getBridges();
	}
	
	public Iterable<? extends DirectedArc<V>> getBridges() {
		final var result = new ArrayList<DirectedArc<V>>();
		
		for(var b : res.keySet())
			for(var e : res.get(b))
				result.add(new DirectedArc<>(b, e));
			
		return result;
	}
	
	private static Integer min(Integer a, Integer b) {
		if(a > b)
			return b;
		return a;
	}
	
	private void dfs(V v, V p) {
		used.put(v, true);
		tin.put(v, timer);
		fup.put(v, timer);
		timer++;
		for(var to : graph.getJoints(v)) {
			if(to == p)
				continue;
			if(used.get(to))
				fup.put(v, min(fup.get(v), tin.get(to)));
			else {
				dfs(to, v);
				fup.put(v, min(fup.get(v), fup.get(to)));
				if(fup.get(to) > tin.get(v))
					res.get(v).add(to);
			}
		}
	}
	
	private void dfs(V v) {
		dfs(v, null);
	}
	
}
