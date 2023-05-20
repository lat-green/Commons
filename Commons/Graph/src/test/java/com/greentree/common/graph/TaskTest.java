package com.greentree.common.graph;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.Test;

import com.greentree.commons.graph.DirectedGraph;
import com.greentree.commons.graph.Graph;
import com.greentree.commons.graph.RootTree;
import com.greentree.commons.graph.RootTreeBase;
import com.greentree.commons.graph.algorithm.path.AbstractTreeVistor;
import com.greentree.commons.graph.algorithm.path.MinPathFinder;
import com.greentree.commons.graph.algorithm.walk.BFSWalker;

public class TaskTest {
	
	@Test
	void test1() {
		final var graph = new StateGraph();
		
		final var start = new State(0, 0);
		final var finish = new State(4, 0);
		
		
		final var finder = new MinPathFinder<>(new BFSWalker<>(graph));
		
		final var solve = finder.getPath(start, finish);
		assertEquals(solve, List.of(new State(0, 0), new State(5, 0), new State(2, 3), new State(2, 0), new State(0, 2),
				new State(5, 2), new State(4, 3), new State(4, 0)));
	}
	
	@Test
	void test2() {
		final var g = new DirectedGraph<String>();
		
		g.add("A", "B");
		
		final var v = new AbstractTreeVistor<String>() {
			
			RootTree<String> max;
			
			@Override
			protected boolean add(RootTree<String> tree) {
				if((max == null) || (max.size() < tree.size()))
					max = tree;
				return true;
			}
		};
		
		g.walker().visit(v);
		
		final var t1 = new RootTreeBase<>("A");
		t1.add("B", "A");
		
		assertEquals(v.max, t1);
	}
	
	private static final class State {
		
		private int v1, v2;
		
		public State(int v1, int v2) {
			this.v1 = v1;
			this.v2 = v2;
		}
		
		@Override
		public boolean equals(Object obj) {
			if(this == obj)
				return true;
			if(!(obj instanceof State))
				return false;
			var other = (State) obj;
			return v1 == other.v1 && v2 == other.v2;
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(v1, v2);
		}
		
		@Override
		public String toString() {
			return "State [" + v1 + ", " + v2 + "]";
		}
		
	}
	
	private static final class StateGraph implements Graph<State> {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public Iterable<? extends State> getJoints(Object v) {
			final var s = (State) v;
			final var result = new HashSet<State>();
			
			result.add(new State(5, s.v2));
			result.add(new State(0, s.v2));
			
			result.add(new State(s.v1, 3));
			result.add(new State(s.v1, 0));
			
			final var x3 = Math.min(s.v1 + s.v2, 3);
			result.add(new State(s.v1 + s.v2 - x3, x3));
			
			final var x5 = Math.min(s.v1 + s.v2, 5);
			result.add(new State(x5, s.v1 + s.v2 - x5));
			
			return result;
		}
		
		@Override
		public Iterator<State> iterator() {
			throw new UnsupportedOperationException("get vertex of infinity graph");
		}
		
	}
	
}
