package com.greentree.commons.util.pool;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GenerateSoftStackObjectPoolTests {

    private static int VECTOR_COUNT = 0;

    private final static class Vector {

        public int x;

        public Vector(int x) {
            this.x = x;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Vector other = (Vector) obj;
            return x == other.x;
        }

        @Override
        public String toString() {
            String builder = "Vector [x=" +
                             x +
                             "]";
            return builder;
        }

    }

    @Test
    void test1() {
        final var pool = new_pool();
        try (final var ref = pool.get()) {
            final var v = ref.get();
            assertNotNull(v);
        }
    }

    private static GenerateSoftStackObjectPool<Vector> new_pool() {
        return new GenerateSoftStackObjectPool<>() {

            @Override
            public Vector generate() {
                return new_vec();
            }

        };
    }

    private static Vector new_vec() {
        return new Vector(VECTOR_COUNT++);
    }

}
