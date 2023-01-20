package com.greentree.common.graph.algorithm.component;

import java.util.Collection;
import java.util.List;

public interface ConnectivityComponentsFinder<V> {
	
	default int getComponent(V vertex) {
		final var cs = getComponents();
		for(int i = 0; i < cs.size(); i++) {
			final var c = cs.get(i);
			if(c.contains(vertex))
				return i;
		}
		throw new IllegalArgumentException("not vertex");
	}
	
	List<? extends Collection<V>> getComponents();
	
}
