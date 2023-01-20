package com.greentree.common.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.greentree.common.graph.algorithm.brige.BridgeFinder;
import com.greentree.common.graph.algorithm.component.ConnectivityComponentsFinder;
import com.greentree.common.graph.algorithm.cycle.CycleFinder;
import com.greentree.common.graph.algorithm.cycle.VertexCycle;
import com.greentree.common.util.iterator.IteratorUtil;

public interface Tree<V> extends Graph<V>, CycleFinder<V>, BridgeFinder<V> {
	
	@Override
	default Iterable<? extends DirectedArc<V>> getBridges() {
		return IteratorUtil.empty();
	}
	
	@Override
	default Iterable<? extends VertexCycle<V>> getCycles() {
		return IteratorUtil.empty();
	}
	
	@Override
	default CycleFinder<V> getCycleFinder() {
		return this;
	}
	
	@Override
	default BridgeFinder<V> getBridgeFinder() {
		return this;
	}
	
	@Override
	default ConnectivityComponentsFinder<V> getConnectivityComponentsFinder() {
		return new TreeConnectivityComponentsFinder<>(this);
	}
	
	public static final class TreeConnectivityComponentsFinder<V>
			implements ConnectivityComponentsFinder<V> {
		
		private final Tree<? extends V> tree;
		
		public TreeConnectivityComponentsFinder(Tree<? extends V> tree) {
			this.tree = tree;
		}
		
		@Override
		public int getComponent(V vertex) {
			if(tree.has(vertex))
				return 0;
			throw new IllegalArgumentException("not vertex");
		}
		
		@Override
		public List<? extends Collection<V>> getComponents() {
			final var result = new ArrayList<Collection<V>>();
			final var c = new ArrayList<V>();
			result.add(c);
			for(var v : tree)
				c.add(v);
			return result;
		}
		
	}
	
}
