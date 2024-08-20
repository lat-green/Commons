package com.greentree.commons.util.iterator;

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

public class GeneratorTest {

    @Test
    void iterator_Consumer() {
        final Iterator<Integer> iter = IteratorUtil.iterator(ret -> {
            ret.accept(5);
            ret.accept(3);
        });
        assertTrue(iter.hasNext());
        assertEquals(iter.next(), 5);
        assertTrue(iter.hasNext());
        assertEquals(iter.next(), 3);
        assertFalse(iter.hasNext());
        assertNull(iter.next());
    }
    //	@Test
    //	void ConsumerGenerator() {
    //		final Iterator<Integer> iter = IteratorUtil.generator(ret -> {
    //			ret.accept(5);
    //			ret.accept(3);
    //		});
    //
    //		assertTrue(iter.hasNext());
    //		assertEquals(iter.next(), 5);
    //		assertTrue(iter.hasNext());
    //		assertEquals(iter.next(), 3);
    //		assertFalse(iter.hasNext());
    //		assertNull(iter.next());
    //	}

    @Test
    void SupplierGenerator() {
        final Iterable<Integer> gen = IteratorUtil.iterable(new Supplier<>() {

            int i = 2;

            @Override
            public Supplier<Integer> get() {
                if (i == 0)
                    return null;
                return () -> i--;
            }

        });
        final var iter = gen.iterator();
        assertTrue(iter.hasNext());
        assertEquals(iter.next(), 2);
        assertTrue(iter.hasNext());
        assertEquals(iter.next(), 1);
        assertFalse(iter.hasNext());
        assertNull(iter.next());

    }

}
