package com.greentree.commons.graph.algorithm.walk;

import com.greentree.commons.graph.Graph;

public interface GraphWalker<V> {
	
	Graph<? extends V> graph();
	
	
	default void visit(GraphVisitor<? super V> visitor) {
		//		final var used_all = new HashSet<V>();
		//		final var bv = new ProxyGraphVisitor<V>(visitor) {
		//			
		//			@Override
		//			public boolean startVisit(V parent, V v) {
		//				used_all.add(v);
		//				return super.startVisit(parent, v);
		//			}
		//			
		//			@Override
		//			public boolean startVisit(V v) {
		//				used_all.add(v);
		//				return super.startVisit(v);
		//			}
		//			
		//		};
		//		for(var v : graph()) {
		//			if(!used_all.contains(v))
		//				visit(v, bv);
		//		}
		for(var v : graph())
			visit(v, visitor);
	}
	
	void visit(V start, GraphVisitor<? super V> visitor);
	
}
