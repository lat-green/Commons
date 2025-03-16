package com.greentree.commons.geometry.geom2d.collision.strategy.world;

import com.greentree.commons.geometry.geom2d.collision.Collidable2D;
import kotlin.Pair;

import java.util.Collection;

public class QuadTreeWorldCollisionStrategy<T extends Collidable2D>
        extends WorldCollisionStrategy<T> {

    @Override
    protected void add0(T shape) {
        // TODO Auto-generated method stub
    }

    @Override
    public Collection<Pair<T, T>> getCollisionContact() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected void remove0(T shape) {
        // TODO Auto-generated method stub
    }

}
