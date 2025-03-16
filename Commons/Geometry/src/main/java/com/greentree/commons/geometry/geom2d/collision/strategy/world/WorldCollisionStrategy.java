package com.greentree.commons.geometry.geom2d.collision.strategy.world;

import com.greentree.commons.geometry.geom2d.Shape2DUtil;
import com.greentree.commons.geometry.geom2d.collision.Collidable2D;
import com.greentree.commons.geometry.geom2d.collision.CollisionEvent2D;
import kotlin.Pair;

import java.util.ArrayList;
import java.util.Collection;

public abstract class WorldCollisionStrategy<T extends Collidable2D> {

    public WorldCollisionStrategy() {
    }

    public WorldCollisionStrategy(Iterable<T> iterable) {
        for (var s : iterable)
            add(s);
    }

    public final void add(T shape) {
        add0(shape);
    }

    protected abstract void add0(T shape);

    public WorldCollisionStrategy(T... array) {
        for (var s : array)
            add(s);
    }

    public final Collection<CollisionEvent2D<T, T>> getCollisions() {
        var contacts = getCollisionContact();
        var res = new ArrayList<CollisionEvent2D<T, T>>(contacts.size());
        for (var pair : contacts)
            res.add(Shape2DUtil.getCollisionEvent(pair.getFirst(), pair.getSecond()));
        return res;
    }

    public abstract Collection<? extends Pair<T, T>> getCollisionContact();

    public final void remove(T shape) {
        remove0(shape);
    }

    protected abstract void remove0(T shape);

    protected void reset0(T shape) {
        remove0(shape);
        add0(shape);
    }

}
