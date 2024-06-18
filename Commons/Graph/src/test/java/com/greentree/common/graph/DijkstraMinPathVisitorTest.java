package com.greentree.common.graph;

import com.greentree.commons.graph.DirectedGraph;
import com.greentree.commons.graph.algorithm.path.DijkstraMinPathFinder;
import com.greentree.commons.graph.algorithm.path.VertexPath;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DijkstraMinPathVisitorTest {

    @Test
    void test1() {
        final var g = new DirectedGraph<String>();
        g.add("A", "B");
        g.add("B", "C");
        g.add("C", "D");
        g.add("D");
        final var pathfinder = new DijkstraMinPathFinder<>(g, "A");
        assertEquals(pathfinder.get("B"), new VertexPath<>("A", List.of("B")));
        assertEquals(pathfinder.get("C"), new VertexPath<>("A", List.of("B", "C")));

    }

}
