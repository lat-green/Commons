package com.greentree.commons.util.collection;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class BorMapTest {

    @Test
    void test1() {
        final var map = new BorMap<String>();
        map.put("A", "B");
        final var b = map.get("A");
        assertEquals(b, "B");
    }

    @Test
    void test2() {
        final var map = new BorMap<String>();
        map.put("A", "B");
        map.remove("A");
        assertNull(map.get("A"));
    }

}
