package com.greentree.commons.util.iterator;

import org.junit.jupiter.api.Test;

import static com.greentree.commons.util.iterator.IterAssertions.assertEqualsAsList;

public class InverseIteratorTest {

    @Test
    void test1() {
        final var list = IteratorUtil.iterator("A", "B", "C");
        final var inv = IteratorUtil.inverse(list);
        assertEqualsAsList(inv, IteratorUtil.iterator("C", "B", "A"));

    }

}
