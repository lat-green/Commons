package com.greentree.common.graph;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.greentree.commons.graph.RootTreeBase;

public class LcaTest {
	
	@Test
	void test1() {
		final var tree = new RootTreeBase<>("A");
		
		tree.add("B", "A");
		tree.add("C", "A");
		tree.add("D", "B");
		tree.add("E", "B");
		
		final var a = tree.lca("D", "C");
		
		assertEquals(a, "A");
		
	}
	
}
