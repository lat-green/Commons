package com.greentree.commons.tests;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IterableAssertions {

    public static <E> void assertEqualsAsSet(Iterable<? extends E> iter1, Iterable<? extends E> iter2) {
        assertEqualsAsSet(iter1.iterator(), iter2.iterator());
    }

    public static <E> void assertEqualsAsSet(Iterator<? extends E> iter1, Iterator<? extends E> iter2) {
        final var set1 = new HashSet<E>();
        final var set2 = new HashSet<E>();
        while (iter1.hasNext())
            set1.add(iter1.next());
        while (iter2.hasNext())
            set2.add(iter2.next());
        assertEquals(set1, set2);
    }

    public static <E> void assertEqualsAsList(Iterable<? extends E> iter1, Iterable<? extends E> iter2) {
        assertEqualsAsList(iter1.iterator(), iter2.iterator());
    }

    public static <E> void assertEqualsAsList(Iterator<? extends E> iter1, Iterator<? extends E> iter2) {
        final var list1 = new ArrayList<E>();
        final var list2 = new ArrayList<E>();
        while (iter1.hasNext())
            list1.add(iter1.next());
        while (iter2.hasNext())
            list2.add(iter2.next());
        assertEquals(list1, list2);
    }

}
