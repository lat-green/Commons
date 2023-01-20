package com.greentree.commons.graph.algorithm.walk;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import com.greentree.commons.graph.Graph;

public final class BFSWalker<V> implements GraphWalker<V> {
	
	
	private static final class BFSWalkInfo<V> {
		
		private final Stack<V> path = new Stack<>();
		
		private BFSWalkInfo() {
		}
		
		private BFSWalkInfo(BFSWalkInfo<V> p, V v) {
			path.addAll(p.path);
			path.add(v);
		}
		
		private BFSWalkInfo(V v) {
			path.add(v);
		}
		
		public boolean contains(V to) {
			return path.contains(to);
		}
		
		public V vertex() {
			return path.peek();
		}
		
	}
	
	private final Graph<? extends V> graph;
	
	public BFSWalker(Graph<? extends V> graph) {
		this.graph = graph;
	}
	
	@Override
	public void visit(V start, GraphVisitor<? super V> visitor) {
		final Queue<BFSWalkInfo<V>> queue = new LinkedList<>();
		visit(queue, visitor, new BFSWalkInfo<>(start));
		while(!queue.isEmpty()) {
			final var v = queue.remove();
			visit(queue, visitor, v);
		}
	}
	
	@Override
	public Graph<? extends V> graph() {
		return graph;
	}
	
	private void visit(Queue<BFSWalkInfo<V>> queue, GraphVisitor<? super V> visitor,
			BFSWalkInfo<V> info) {
		{
			final var v = info.vertex();
			var deadlock = true;
			for(var to : graph.getJoints(v))
				if(!info.contains(to)) {
					final var to_info = new BFSWalkInfo<>(info, to);
					queue.add(to_info);
					deadlock = false;
				}
			if(!deadlock)
				return;
		}
		GraphWalker.visitPath(info.path.iterator(), visitor);
	}
	
}
