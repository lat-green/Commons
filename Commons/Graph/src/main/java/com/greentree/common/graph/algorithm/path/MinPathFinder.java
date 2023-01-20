package com.greentree.common.graph.algorithm.path;

import java.util.List;
import java.util.function.BiFunction;

import com.greentree.common.graph.Graph;
import com.greentree.common.graph.algorithm.walk.DFSWalker;
import com.greentree.common.graph.algorithm.walk.GraphVisitor;
import com.greentree.common.graph.algorithm.walk.GraphWalker;

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
	
	
	public class Visitor implements GraphVisitor<V> {
		
		private List<? extends V> result;
		private double result_length = Double.MAX_VALUE;
		
		private final GraphVisitor<V> visitor;
		private double path_length;
		
		
		public Visitor(V end) {
			visitor = new AbstractPathVistor<>(end) {
				
				@Override
				protected boolean add(List<V> path) {
					setResult(path);
					return true;
				}
				
			};
		}
		
		@Override
		public void endVisit(V v) {
			visitor.endVisit(v);
		}
		
		@Override
		public void endVisit(V parent, V v) {
			visitor.endVisit(v);
			final var l = arc_len.apply(parent, v).doubleValue();
			path_length -= l;
		}
		
		public void setResult(List<? extends V> path) {
			if(result == null || path_length < result_length) {
				result_length = path_length;
				result = path;
			}
		}
		
		@Override
		public boolean startVisit(V v) {
			return visitor.startVisit(v);
		}
		
		@Override
		public boolean startVisit(V parent, V v) {
			final var l = arc_len.apply(parent, v).doubleValue();
			path_length += l;
			return visitor.startVisit(v) && path_length < result_length;
		}
		
	}
	
}
