package com.greentree.commons.graph.algorithm.path;

import java.util.function.Predicate;

public interface HasPath<V> {
	
	boolean hasPath(Object begin, Object end);
	
	default Predicate<? super Object> hasPathFrom(Object begin) {
		return end->hasPath(begin, end);
	}
	
	default Predicate<? super Object> hasPathTo(Object end) {
		return begin->hasPath(begin, end);
	}
	
}
