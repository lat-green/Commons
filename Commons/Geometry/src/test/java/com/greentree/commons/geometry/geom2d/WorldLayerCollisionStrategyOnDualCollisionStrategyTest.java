package com.greentree.commons.geometry.geom2d;

import com.greentree.commons.geometry.geom2d.collision.WorldLayerCollisionStrategyOnDualCollisionStrategy;
import com.greentree.commons.geometry.geom2d.collision.strategy.dual.BruteDualCollisionStrategy;
import com.greentree.commons.geometry.geom2d.shape.Circle2D;
import com.greentree.commons.util.collection.TransientTable;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class WorldLayerCollisionStrategyOnDualCollisionStrategyTest {

    @Test
    void test1() {
        TransientTable<Integer, Boolean> is = new TransientTable<>();
        is.put(0, 1, true);
        is.put(0, 2, true);
        is.put(1, 2, true);
        WorldLayerCollisionStrategyOnDualCollisionStrategy<WrapCollidable> strategy = new WorldLayerCollisionStrategyOnDualCollisionStrategy<>(is, new BruteDualCollisionStrategy<>());
        final var s1 = new Circle2D(10f, 0f);
        final var s2 = new Circle2D(-10f, 0f);
        final var s3 = new Circle2D();
        strategy.add(0, new WrapCollidable(s1));
        strategy.add(0, new WrapCollidable(s2));
        strategy.add(0, new WrapCollidable(s3));
        var res = strategy.getCollisionContacts();
        assertTrue(res.isEmpty());
    }

}
