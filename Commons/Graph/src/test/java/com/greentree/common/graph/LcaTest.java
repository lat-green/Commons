package com.greentree.common.graph;

import com.greentree.commons.graph.RootTree;
import com.greentree.commons.graph.RootTreeBase;
import org.junit.jupiter.api.Test;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    void classTree() {
        final var tree = new RootTree<Class<?>>() {

            @Override
            public Iterator<Class<?>> iterator() {
                return null;
            }

            @Override
            public boolean containsInSubTree(Object v, Object parent) {
                var cls = (Class<?>) v;
                var superClass = (Class<?>) parent;
                return superClass.isAssignableFrom(cls);
            }

            @Override
            public Class<?> getParent(Object v) {
                if (v == Object.class)
                    return Object.class;
                return ((Class<?>) v).getSuperclass();
            }
        };
        final var a = tree.lca(ArrayList.class, LinkedList.class);
        assertEquals(a, AbstractList.class);
    }

}
