package com.greentree.commons.graph.algorithm.walk;

import java.util.Iterator;

import com.greentree.commons.graph.Graph;

public interface GraphWalker<V> {
	
	private static <V> void dfs(Iterator<? extends V> path, V p, GraphVisitor<? super V> visitor) {
		if(!path.hasNext())
			return;
		final var v = path.next();
		if(visitor.startVisit(p, v))
			dfs(path, v, visitor);
		visitor.endVisit(p, v);
	}
	
	static <V> void visitPath(Iterator<? extends V> path, GraphVisitor<? super V> visitor) {
		final var v = path.next();
		if(visitor.startVisit(v))
			dfs(path, v, visitor);
		visitor.endVisit(v);
	}
	
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
