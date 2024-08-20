package com.greentree.commons.util.iterator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArrayIterableTest {

    @Test
    void testSize() {
        assertEquals(new ArrayIterable<>("A", "B").size(), 2);
    }

}
