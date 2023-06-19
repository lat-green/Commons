package com.greentree.commons.geometry.geom2d.collision.strategy;

import java.util.Collection;

import com.greentree.commons.geometry.geom2d.collision.Collidable2D;
import com.greentree.commons.geometry.geom2d.collision.CollisionEvent2D;

public abstract class CollisionStrategy {

	public abstract <T extends Collidable2D> Collection<CollisionEvent2D<T, T>> getCollisions(T...world);

}
