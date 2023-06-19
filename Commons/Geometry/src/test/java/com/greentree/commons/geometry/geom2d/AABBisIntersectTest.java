package com.greentree.commons.geometry.geom2d;

import com.greentree.commons.geometry.geom2d.shape.Circle2D;
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
        final var shape1 = new Circle2D(0, 0, 1);
        final var shape2 = new Circle2D(0, 0, 1);
        final var shape3 = new Circle2D(3, 0, 1);
        final var shape4 = new Circle2D(0, 3, 1);
        final var shape5 = new Circle2D(2, 2, 1.5f);
        assertTrue(shape1.getAABB().isIntersect(shape2.getAABB()));
        assertFalse(shape1.getAABB().isIntersect(shape3.getAABB()));
        assertFalse(shape1.getAABB().isIntersect(shape4.getAABB()));
        assertTrue(shape1.getAABB().isIntersect(shape5.getAABB()));
        assertFalse(shape2.getAABB().isIntersect(shape3.getAABB()));
        assertFalse(shape2.getAABB().isIntersect(shape4.getAABB()));
        assertTrue(shape2.getAABB().isIntersect(shape5.getAABB()));
        assertFalse(shape3.getAABB().isIntersect(shape4.getAABB()));
        assertTrue(shape3.getAABB().isIntersect(shape5.getAABB()));
        assertTrue(shape4.getAABB().isIntersect(shape5.getAABB()));

    }

}
