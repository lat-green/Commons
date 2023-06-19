package com.greentree.commons.geometry.geom2d;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Arseny Latyshev
 */
@DisplayName("AABB 2D isIntersect Tests")
public class AABBisIntersectTest {

    @DisplayName("Circle as AABB isIntersect")
    @Test
    public void Circle() {
        final var shape1 = new Circle(0, 0, 1);
        final var shape2 = new Circle(0, 0, 1);
        final var shape3 = new Circle(3, 0, 1);
        final var shape4 = new Circle(0, 3, 1);
        final var shape5 = new Circle(2, 2, 1.51f);
        assertIntersect(shape1.aabb(), shape2.aabb());
        assertNotIntersect(shape1.aabb(), shape3.aabb());
        assertNotIntersect(shape1.aabb(), shape4.aabb());
        assertIntersect(shape1.aabb(), shape5.aabb());
        assertNotIntersect(shape2.aabb(), shape3.aabb());
        assertNotIntersect(shape2.aabb(), shape4.aabb());
        assertIntersect(shape2.aabb(), shape5.aabb());
        assertNotIntersect(shape3.aabb(), shape4.aabb());
        assertIntersect(shape3.aabb(), shape5.aabb());
        assertIntersect(shape4.aabb(), shape5.aabb());

    }

    private void assertIntersect(AABB a, AABB b) {
        assertTrue(a.isIntersect(b), a + " " + b);
    }

    private void assertNotIntersect(AABB a, AABB b) {
        assertFalse(a.isIntersect(b), a + " " + b);
    }

}
