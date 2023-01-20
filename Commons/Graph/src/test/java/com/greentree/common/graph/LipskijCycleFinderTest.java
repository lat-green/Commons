package com.greentree.common.graph;

import org.junit.jupiter.api.Test;

public class LipskijCycleFinderTest {

	public static <T> void addUnoriented(DirectedGraph<T> graph, T a, T b) {
		graph.add(a, b);
		graph.add(b, a);
	}

	@Test
	void find() {
		DirectedGraph<String> graph = new DirectedGraph<>();

		addUnoriented(graph, "A", "B");
		addUnoriented(graph, "A", "D");
		addUnoriented(graph, "C", "B");
		addUnoriented(graph, "C", "D");

		addUnoriented(graph, "A+", "B+");
		addUnoriented(graph, "A+", "D+");
		addUnoriented(graph, "C+", "B+");
		addUnoriented(graph, "C+", "D+");

		addUnoriented(graph, "A", "A+");
		addUnoriented(graph, "A", "B+");
		addUnoriented(graph, "A+", "0");

		//		var finder = new CycleFinder2<>(graph);

		//		System.out.println(finder.get());
		//		System.out.println(finder.get().size());

	}

}
