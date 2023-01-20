package com.greentree.common.graph.algorithm.walk;


public interface GraphVisitor<V> {
	
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
