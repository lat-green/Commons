package com.greentree.commons.graph.algorithm.path;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public interface MiddlePathFinder<V> extends PathFinder<V>, HasPath<V> {
	
	/** @return middle in path begin to end, or null if path not exist */
	V getPathMidle(Object begin, Object end);
	
	@Override
	default boolean hasPath(Object begin, Object end) {
		return getPathMidle(begin, end) != null;
	}
	
	@Override
	default Predicate<? super Object> hasPathFrom(Object v) {
		throw new UnsupportedOperationException();
	}
	
	default VertexPath<V> getPath(V begin, V end) {
		final var path = getPath0(this, begin, end);
		if(path == null)
			return null;
		return new VertexPath<>(begin, path);
	}
	
	private static <V> List<V> getPath0(MiddlePathFinder<V> finder, V begin, V end) {
		if(begin.equals(end))
			return new ArrayList<>();
		final var m = finder.getPathMidle(begin, end);
		if(m == null)
			return null;
		if(m.equals(end))
			return List.of(end);
		final var a = getPath0(finder, begin, m);
		final var b = getPath0(finder, m, end);
		
		a.addAll(b);
		
		return a;
	}
	
}
