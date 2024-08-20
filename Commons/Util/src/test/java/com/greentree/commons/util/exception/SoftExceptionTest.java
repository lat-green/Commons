package com.greentree.commons.util.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SoftExceptionTest {

    @Test
    void test1() {
        final var e1 = new NullPointerException("a");
        final var e2 = new NullPointerException("b");
        final var e = assertThrows(NullPointerException.class, () -> {
            try {
                throw e1;
            } catch (Exception e0) {
                e0.addSuppressed(e2);
                throw e0;
            }
        });
        assertEquals(e, e1);
    }

    @Test
    void test2() {
        final var e1 = new NullPointerException("a");
        final var e2 = new NullPointerException("b");
        final var e = assertThrows(NullPointerException.class, () -> {
            try {
                throw e1;
            } catch (Exception e0) {
                throw e2;
            }
        });
        assertEquals(e, e2);
    }

    @Test
    void test3() {
        final var e1 = new NullPointerException("a");
        final var e2 = new NullPointerException("b");
        final var e = assertThrows(NullPointerException.class, () -> {
            SoftException.run(e1, () -> {
                throw e2;
            });
        });
        assertEquals(e, e1);
    }

}
