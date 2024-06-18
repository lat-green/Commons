package com.greentree.common.graph

import com.greentree.commons.graph.DirectedGraph
import com.greentree.commons.graph.algorithm.sort.topologicalSort
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class TopologicalSortTest {

	@Test
	fun test1() {
		val graph = DirectedGraph<String>()
		graph.add("A", "C")
		graph.add("C", "B")
		graph.add("B", "D")
		val result = graph.topologicalSort
		assertEquals(result.size, graph.size)
		assertEquals(result, listOf("A", "C", "B", "D"))
	}

	@Test
	fun test2() {
		val graph = DirectedGraph<String>()
		graph.add("A", "B")
		graph.add("A", "C")
		graph.add("A", "D")
		graph.add("C", "B")
		graph.add("C", "D")
		graph.add("B", "D")
		val result = graph.topologicalSort
		assertEquals(result.size, graph.size)
		assertEquals(result, listOf("A", "C", "B", "D"))
	}

	@Test
	fun test3() {
		val graph = DirectedGraph<String>()
		graph.add("A", "B")
		graph.add("A", "C")
		graph.add("A", "D")
		graph.add("C", "D")
		graph.add("C", "B")
		graph.add("B", "D")
		val result = graph.topologicalSort
		assertEquals(result.size, graph.size)
		assertEquals(result, listOf("A", "C", "B", "D"))
	}
}
