package com.greentree.commons.util.iterator;

import org.junit.jupiter.api.Test;

import static com.greentree.commons.util.iterator.IterAssertions.assertEqualsAsSet;

public class CrossIteratorTest {

    @Test
    void test1() {
        final var i1 = IteratorUtil.iterable("A", "C", "D", "E");
        final var i2 = IteratorUtil.iterable("A", "B", "D");
        final var r = IteratorUtil.cross(i1, i2);
        final var a = IteratorUtil.iterable("A", "D");
        assertEqualsAsSet(r, a);
    }

}
