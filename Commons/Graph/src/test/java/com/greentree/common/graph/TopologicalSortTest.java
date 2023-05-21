package com.greentree.common.graph;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.greentree.commons.graph.DirectedGraph;

public class TopologicalSortTest {
	
	@Test
	void test1() {
		var graph = new DirectedGraph<>();
		
		graph.add("A", "B");
		graph.add("B", "C");
		graph.add("C", "D");
		
		var result = graph.getTopologicalSort();
		
		assertEquals(result, List.of("A", "B", "C", "D"));
	}
	
}
