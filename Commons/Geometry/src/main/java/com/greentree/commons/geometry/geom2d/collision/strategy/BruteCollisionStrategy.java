package com.greentree.commons.geometry.geom2d.collision.strategy;

import com.greentree.commons.geometry.geom2d.Shape2DUtil;
import com.greentree.commons.geometry.geom2d.collision.Collidable2D;
import kotlin.Pair;

import java.util.ArrayList;
import java.util.Collection;

public class BruteCollisionStrategy extends AbstractCollisionStrategy {

    @Override
    public <T extends Collidable2D> Collection<Pair<T, T>> getCollisionContact(T[] world) {
        final Collection<Pair<T, T>> res = new ArrayList<>();
        for (T a : world)
            for (T b : world)
                if (a == b)
                    break;
                else if (Shape2DUtil.isIntersect(a.getShape(), b.getShape()))
                    res.add(new Pair<>(a, b));
        return res;
    }

}
