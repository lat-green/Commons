package com.greentree.common.graph;

import com.greentree.commons.graph.DirectedGraph;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TopologicalSortTest {

    @Test
    void test1() {
        var graph = new DirectedGraph<>();
        graph.add("A", "C");
        graph.add("C", "B");
        graph.add("B", "D");
        var result = graph.getTopologicalSort();
        assertEquals(result, List.of("A", "C", "B", "D"));
    }

    @Test
    void test2() {
        var graph = new DirectedGraph<>();
        graph.add("A", "B");
        graph.add("A", "C");
        graph.add("A", "D");
        graph.add("B", "C");
        graph.add("B", "D");
        graph.add("C", "D");
        var result = graph.getTopologicalSort();
        assertEquals(result, List.of("A", "B", "C", "D"));
    }

    @Test
    void test3() {
        var graph = new DirectedGraph<>();
        graph.add("B", "C");
        graph.add("B", "D");
        graph.add("A", "B");
        graph.add("A", "C");
        graph.add("A", "D");
        graph.add("C", "D");
        var result = graph.getTopologicalSort();
        assertEquals(result, List.of("A", "B", "C", "D"));
    }

}
