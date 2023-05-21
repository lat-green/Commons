package com.greentree.commons.graph.algorithm.walk;

import java.util.Iterator;

public interface GraphVisitor<V> {
	
	default void visitPath(Iterator<? extends V> path, V p) {
		if(!path.hasNext())
			return;
		final var v = path.next();
		if(startVisit(p, v))
			visitPath(path, v);
		endVisit(p, v);
	}
	
	default void visitPath(Iterator<? extends V> path) {
		final var v = path.next();
		if(startVisit(v))
			visitPath(path, v);
		endVisit(v);
	}
	
	default void endVisit(V v) {
	}
	
	default void endVisit(V parent, V v) {
		endVisit(v);
	}
	
	boolean startVisit(V v);
	
	default boolean startVisit(V parent, V v) {
		return startVisit(v);
	}
	
}
