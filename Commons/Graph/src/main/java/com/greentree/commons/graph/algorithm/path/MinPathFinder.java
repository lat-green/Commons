package com.greentree.commons.graph.algorithm.path;

import java.util.List;
import java.util.function.BiFunction;

import com.greentree.commons.graph.Graph;
import com.greentree.commons.graph.algorithm.walk.DFSWalker;
import com.greentree.commons.graph.algorithm.walk.GraphWalker;

public class MinPathFinder<V> implements PathFinder<V> {
	
	private final BiFunction<? super V, ? super V, ? extends Number> arc_len;
	
	private final GraphWalker<V> walker;
	
	public MinPathFinder(Graph<? extends V> graph) {
		this(graph, (a, b)->1);
	}
	
	public MinPathFinder(Graph<? extends V> graph,
			BiFunction<? super V, ? super V, ? extends Number> arc_len) {
		this(new DFSWalker<>(graph), arc_len);
	}
	
	public MinPathFinder(GraphWalker<V> walker) {
		this(walker, (a, b)->1);
	}
	
	public MinPathFinder(GraphWalker<V> walker,
			BiFunction<? super V, ? super V, ? extends Number> arc_len) {
		this.walker = walker;
		this.arc_len = arc_len;
	}
	
	
	public static <V> VertexPath<V> get(Graph<? extends V> graph, V begin, V end) {
		return get(new DFSWalker<>(graph), begin, end);
	}
	
	public static <V> VertexPath<V> get(GraphWalker<V> walker, V begin, V end) {
		return new MinPathFinder<>(walker).getPath(begin, end);
	}
	
	
	@Override
	public VertexPath<V> getPath(V a, V b) {
		final var visitor = new Visitor(b);
		walker.visit(a, visitor);
		return new VertexPath<>(visitor.result, arc_len);
	}
	
	
	@Override
	public String toString() {
		return "MinPathFinder [" + arc_len + ", " + walker + "]";
	}
	
	
	public class Visitor extends AbstractPathVistor<V> {
		
		private List<? extends V> result;
		private double result_length = Double.MAX_VALUE;
		
		private double path_length;
		
		
		public Visitor(V end) {
			super(end);
		}
		
		@Override
		public void endVisit(V parent, V v) {
			super.endVisit(parent, v);
			final var l = arc_len.apply(parent, v).doubleValue();
			path_length -= l;
		}
		
		@Override
		public boolean startVisit(V parent, V v) {
			final var l = arc_len.apply(parent, v).doubleValue();
			path_length += l;
			return super.startVisit(parent, v) && path_length < result_length;
		}
		
		@Override
		protected boolean add(List<V> path) {
			if(result == null || path_length < result_length) {
				result_length = path_length;
				result = path;
			}
			return true;
		}
		
	}
	
}
