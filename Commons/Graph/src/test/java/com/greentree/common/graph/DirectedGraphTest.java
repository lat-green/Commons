package com.greentree.common.graph;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import com.greentree.common.graph.algorithm.brige.BridgeFinderImpl;
import com.greentree.common.graph.algorithm.cycle.CycleFinderImpl;
import com.greentree.common.graph.algorithm.path.AllPathFinder;
import com.greentree.common.graph.algorithm.path.MinPathFinder;
import com.greentree.common.graph.algorithm.path.SmartAllPathFinder;

public class DirectedGraphTest {
	
	static SmartAllPathFinder<Integer> finder;
	static DirectedGraph<Integer> graph;
	
	@BeforeAll
	static void genGraphs() {
		graph = genGraphs(20, 75);
		
		finder = new SmartAllPathFinder<>(graph);
	}
	
	static DirectedGraph<Integer> genGraphs(int n, int m) {
		DirectedGraph<Integer> graph = new DirectedGraph<>();
		Random rand = new Random();
		
		for(int i = 0; i < n; i++)
			graph.add(i);
		
		for(int i = 0; i < m; i++) {
			int a;
			int b;
			do {
				a = rand.nextInt(n);
				b = rand.nextInt(n);
			}while(graph.has(a, b) || a == b);
			graph.add(a, b);
		}
		
		return graph;
	}
	
	static DirectedGraph<Integer> genGraphs2(int n, int m) {
		DirectedGraph<Integer> graph = new DirectedGraph<>();
		Random rand = new Random();
		
		for(int i = 0; i < n; i++)
			graph.add(i);
		
		for(int i = 0; i < m; i++) {
			int a;
			int b;
			do {
				a = rand.nextInt(n);
				b = rand.nextInt(n);
			}while(graph.has(a, b));
			graph.add(a, b);
			graph.add(b, a);
		}
		
		return graph;
	}
	
	static DirectedGraph<Integer> genGraphsFull(int n) {
		DirectedGraph<Integer> graph = new DirectedGraph<>();
		
		for(int a = 0; a < n; a++)
			for(int b = 0; b < n; b++)
				graph.add(a, b);
			
		return graph;
	}
	
	
	@Test
	void getAllPaths() {
		DirectedGraph<Integer> graph = new DirectedGraph<>();
		
		graph.add(0, 1);
		graph.add(1, 2);
		graph.add(0, 3);
		graph.add(3, 4);
		graph.add(4, 5);
		graph.add(5, 2);
		
		var res = AllPathFinder.get(graph, 0, 2);
		assertEquals(res.size(), 2, res.toString());
		assertTrue(res.contains(Arrays.asList(0, 1, 2)), res.toString());
		assertTrue(res.contains(Arrays.asList(0, 3, 4, 5, 2)), res.toString());
	}
	
	@Test
	void getAllPathsOneVertex1() {
		DirectedGraph<Integer> graph = new DirectedGraph<>();
		
		graph.add(0);
		
		var res = AllPathFinder.get(graph, 0, 0);
		assertEquals(res.size(), 1, res.toString());
		assertTrue(res.contains(Arrays.asList(0)), res.toString());
	}
	
	@Test
	void getBridge() {
		DirectedGraph<Integer> graph = new DirectedGraph<>();
		
		graph.add(0, 1);
		graph.add(1, 2);
		graph.add(2, 0);
		graph.add(2, 3);
		
		var res = BridgeFinderImpl.getBridges(graph);
		assertEquals(res, List.of(new DirectedArc<>(2, 3)));
	}
	
	@Test
	void getCycleNotCycle() {
		DirectedGraph<Integer> graph = new DirectedGraph<>();
		
		graph.add(0, 1);
		graph.add(1, 2);
		graph.add(2, 3);
		
		var res = CycleFinderImpl.get(graph);
		assertEquals(res.size(), 0, res.toString());
	}
	
	@Test
	void getCycleSomeCyle() {
		DirectedGraph<Integer> graph = new DirectedGraph<>();
		
		graph.add(0, 1);
		graph.add(1, 2);
		graph.add(2, 0);
		
		var res = CycleFinderImpl.get(graph);
		assertEquals(res.size(), 1, res.toString());
	}
	
	
	@Test
	void getPathFinder() {
		DirectedGraph<Integer> graph = new DirectedGraph<>();
		
		graph.add(2);
		
		graph.add(0, 1);
		graph.add(1, 2);
		graph.add(0, 3);
		graph.add(3, 4);
		graph.add(4, 5);
		graph.add(5, 2);
		
		final var finder = new MinPathFinder<>(graph);
		assertEquals(finder.getPath(0, 2), Arrays.asList(0, 1, 2));
	}
	
	@Test
	void getPathFinderLong() {
		DirectedGraph<Integer> graph = new DirectedGraph<>();
		
		for(int i = 0; i < 100; i++)
			graph.add(99 - i);
		
		for(int i = 0; i < 99; i++)
			graph.add(i, i + 1);
		
		List<Integer> ans = new ArrayList<>();
		for(int i = 0; i <= 99; i++)
			ans.add(i);
		
		final var finder = new MinPathFinder<>(graph);
		assertEquals(finder.getPath(0, 99), ans);
	}
	
	@Test
	void getSmartAllPaths() {
		DirectedGraph<Integer> graph = new DirectedGraph<>();
		
		graph.add(0, 1);
		graph.add(1, 2);
		graph.add(0, 3);
		graph.add(3, 4);
		graph.add(4, 2);
		
		SmartAllPathFinder<Integer> finder = new SmartAllPathFinder<>(graph);
		
		var res = finder.get(0, 2);
		assertEquals(res.size(), 2, res.toString());
		assertTrue(res.contains(Arrays.asList(0, 1, 2)), res.toString());
		assertTrue(res.contains(Arrays.asList(0, 3, 4, 2)), res.toString());
	}
	
	@Test
	void test1() {
		DirectedGraph<Integer> graph = new DirectedGraph<>();
		
		graph.add(2);
		
		graph.add(0, 1);
		graph.add(1, 2);
		graph.add(0, 2);
		
		final BiFunction<Integer, Integer, Double> dis = (a, b)-> {
			if(a == 0 && b == 2)
				return 30d;
			return 10d;
		};
		
		final var finder = new MinPathFinder<>(graph, dis);
		var res = finder.getPath(0, 2);
		
		assertEquals(res, Arrays.asList(0, 1, 2));
		assertEquals(res.length(), 20, "found path:" + res);
	}
	
	@Test
	void testOneVertex() {
		DirectedGraph<Integer> graph = new DirectedGraph<>();
		
		graph.add(0);
		
		final var finder = new MinPathFinder<>(graph);
		assertEquals(finder.getPath(0, 0), Arrays.asList(0));
	}
	
	//	@Test
	//	void fullGraph() {
	//		DirectedGraph<Integer> graph = new DirectedGraph<>();
	//
	//		graph.add(0, 1);
	//		graph.add(1, 2);
	//		graph.add(2, 0);
	//		graph.add(2, 3);
	//		graph.add(3);
	//
	//		var res = new FullGraph<>(graph);
	//	}
	
	@Nested
	public class SpeedTestGetAllPaths {
		
		@Test
		void equalsResultSpeedTest() {
			assertEquals(finder.get(0, 1), AllPathFinder.get(graph, 0, 1));
		}
		
		@RepeatedTest(10)
		@Timeout(1)
		void getAllPaths() {
			AllPathFinder.get(graph, 0, 1);
		}
		
		@RepeatedTest(10)
		@Timeout(1)
		void getSmartAllPaths() {
			finder.get(0, 1);
		}
		
	}
	/*
	@Nested
	public class SpeedTestGetCycle {
	
		@RepeatedTest(10)
		@Timeout(2)
		void getCycle() {
			int n = 900, m = 2600;
	
			DirectedGraph<Integer> graph =
	//					new DirectedGraph<>() {
	//				{
	//
	//					add(0, 1);
	//					add(1, 0);
	//					add(1, 2);
	//					add(2, 1);
	//					add(2, 0);
	//					add(0, 2);
	//
	//					add(0, 3);
	//					add(3, 0);
	//
	//					add(3, 4);
	//					add(4, 3);
	//					add(5, 4);
	//					add(4, 5);
	//					add(3, 5);
	//					add(5, 3);
	//				}
	//			};
	
			genGraphs(n, m);
	//			genGraphsFull(3);
	
	//			System.out.println(new FullGraph<>(graph));
	//			System.out.println(graph);
	
			//var res = graph.getVertexCycle();
	
	//			System.out.printf("n=%d m=%d c=%d\n", 4, 4, res.size());
	//			System.out.printf("n=%d m=%d c=C%d\n", n, m, res.size());
	//			System.out.println(res);
		}
	
	}
	//*/
	
}
