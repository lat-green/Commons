package com.greentree.commons.util.iterator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArrayIteratorTest {

    @Test
    void testSize() {
        assertEquals(new ArrayIterator<>("A", "B").size(), 2);
        final var iter = new ArrayIterator<>("A", "B");
        iter.next();
        assertEquals(iter.size(), 1);
    }

}
