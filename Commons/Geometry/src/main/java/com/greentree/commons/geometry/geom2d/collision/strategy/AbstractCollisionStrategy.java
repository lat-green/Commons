package com.greentree.commons.geometry.geom2d.collision.strategy;

import com.greentree.commons.geometry.geom2d.Shape2DUtil;
import com.greentree.commons.geometry.geom2d.collision.Collidable2D;
import com.greentree.commons.geometry.geom2d.collision.CollisionEvent2D;
import kotlin.Pair;

import java.util.Collection;

public abstract class AbstractCollisionStrategy extends CollisionStrategy {

    @SafeVarargs
    @Override
    public final <T extends Collidable2D> Collection<CollisionEvent2D<T, T>> getCollisions(
            T... world) {
        return Shape2DUtil.getCollisions(getCollisionContact(world));
    }

    public abstract <T extends Collidable2D> Collection<Pair<T, T>> getCollisionContact(T[] world);

}
